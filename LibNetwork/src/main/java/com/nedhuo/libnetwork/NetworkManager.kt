package com.nedhuo.libnetwork

import android.content.Context
import com.example.lib_config.core.AppConfig
import com.example.lib_log.LogUtils
import com.nedhuo.libnetwork.interceptor.CacheInterceptor
import com.nedhuo.libnetwork.interceptor.HeaderInterceptor
import com.google.gson.JsonParseException
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * 网络请求管理类
 */
object NetworkManager {
    private const val TAG = "NetworkManager"

    // 从NetworkConfig获取超时时间
    private val timeoutSeconds: Long
        get() = NetworkConfigManager.getConfig().timeout

    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null

    // 存储自定义拦截器
    private val customInterceptors = mutableListOf<Interceptor>()

    /**
     * 初始化网络管理器
     */
    fun init(context: Context) {
        NetworkConfigManager.init(context)
        setupOkHttpClient(context)
    }

    /**
     * 初始化网络管理器并配置参数
     */
    fun init(context: Context, config: NetworkConfig) {
        NetworkConfigManager.init(context, config)
        setupOkHttpClient(context)
    }

    /**
     * 添加自定义拦截器
     */
    fun addInterceptor(interceptor: Interceptor) {
        customInterceptors.add(interceptor)
        // 重置OkHttpClient，使拦截器生效
        okHttpClient = null
    }

    /**
     * 移除自定义拦截器
     */
    fun removeInterceptor(interceptor: Interceptor) {
        customInterceptors.remove(interceptor)
        // 重置OkHttpClient，使移除生效
        okHttpClient = null
    }

    /**
     * 获取 Retrofit 实例
     */
    fun getRetrofit(): Retrofit {
        if (retrofit == null || AppConfig.getBaseUrl() != retrofit!!.baseUrl().toString()) {
            retrofit = createRetrofit()
        }
        return retrofit!!
    }

    /**
     * 获取 OkHttpClient 实例
     */
    fun getOkHttpClient(): OkHttpClient {
        if (okHttpClient == null) {
            okHttpClient = createOkHttpClient()
        }
        return okHttpClient!!
    }

    /**
     * 创建 Retrofit 实例
     */
    private fun createRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConfig.getBaseUrl())
            .client(getOkHttpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    /**
     * 创建 OkHttpClient 实例
     */
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .apply {
                if (AppConfig.isDebug()) {
                    // 在调试模式下添加日志拦截器
                    addInterceptor(HttpLoggingInterceptor { message ->
                        LogUtils.d(TAG, message)
                    }.apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .build()
    }

    /**
     * 重置网络客户端
     * 在切换环境时调用
     */
    fun reset() {
        retrofit = null
        okHttpClient = null
    }

    private fun setupOkHttpClient(context: Context) {
        val builder = OkHttpClient.Builder()
            .connectTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .readTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .writeTimeout(timeoutSeconds, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)

        // 添加Header拦截器
        AppConfig.getHeaders()?.let { headers ->
            if (headers.isNotEmpty()) {
                builder.addInterceptor(HeaderInterceptor(headers))
            }
        }

        // 添加缓存
        if (AppConfig.isEnableCache()) {
            val cacheDir = File(context.cacheDir, "http_cache")
            val cache = Cache(cacheDir, AppConfig.getCacheSize())
            builder.cache(cache)
            builder.addNetworkInterceptor(CacheInterceptor(context))
        }

        // 添加日志拦截器
        if (AppConfig.isShowLog()) {
            val loggingInterceptor = HttpLoggingInterceptor { message ->
                LogUtils.d(TAG, message)
            }.apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            builder.addInterceptor(loggingInterceptor)
        }

        // 配置SSL
        if (AppConfig.isTrustSSL()) {
            setupSSL(builder)
        }

        // 添加自定义拦截器
        customInterceptors.forEach {
            builder.addInterceptor(it)
        }

        okHttpClient = builder.build()
    }

    private fun setupSSL(builder: OkHttpClient.Builder) {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<out X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            builder.sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
        } catch (e: Exception) {
            LogUtils.e("SSL configuration failed", e, TAG)
        }
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return getRetrofit().create(serviceClass)
    }

    private fun mapException(throwable: Throwable): NetworkException {
        return when (throwable) {
            is HttpException -> {
                val errorBody = throwable.response()?.errorBody()?.string()
                val errorMessage = when {
                    errorBody.isNullOrEmpty() -> throwable.message()
                    else -> "${throwable.message()}: $errorBody"
                }

                when (throwable.code()) {
                    401 -> NetworkException.UnauthorizedError()
                    in 400..499 -> NetworkException.HttpError(errorMessage, throwable)
                    in 500..599 -> NetworkException.ServerError(errorMessage, throwable)
                    else -> NetworkException.HttpError(errorMessage, throwable)
                }
            }

            is SocketTimeoutException -> NetworkException.TimeoutError("Request timed out after ${timeoutSeconds} seconds", throwable)
            is UnknownHostException -> NetworkException.NetworkError("Unknown host: please check your network connection", throwable)
            is JsonParseException -> NetworkException.ParseError("Failed to parse response data", throwable)
            is ConnectException -> NetworkException.NetworkError("Failed to connect to server", throwable)
            is CertificateException -> NetworkException.NetworkError("SSL certificate error", throwable)
            else -> NetworkException.NetworkError("Network error occurred", throwable)
        }
    }
}