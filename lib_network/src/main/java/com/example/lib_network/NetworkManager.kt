package com.example.lib_network

import android.content.Context
import com.example.lib_config.core.AppConfig
import com.example.lib_log.LogUtils
import com.example.lib_network.interceptor.CacheInterceptor
import com.example.lib_network.interceptor.HeaderInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

/**
 * 网络请求管理类
 */
object NetworkManager {
    private const val TAG = "NetworkManager"
    private const val TIMEOUT_SECONDS = 30L

    private var retrofit: Retrofit? = null
    private var okHttpClient: OkHttpClient? = null

    /**
     * 初始化网络管理器
     */
    fun init(context: Context) {
        setupOkHttpClient(context)
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
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
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
            .connectTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_SECONDS, TimeUnit.SECONDS)
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

        okHttpClient = builder.build()
    }

    private fun setupSSL(builder: OkHttpClient.Builder) {
        try {
            val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                override fun checkClientTrusted(chain: Array<out java.security.cert.X509Certificate>, authType: String) {}
                override fun checkServerTrusted(chain: Array<out java.security.cert.X509Certificate>, authType: String) {}
                override fun getAcceptedIssuers(): Array<java.security.cert.X509Certificate> = arrayOf()
            })

            val sslContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, java.security.SecureRandom())

            builder.sslSocketFactory(sslContext.socketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier { _, _ -> true }
        } catch (e: Exception) {
            LogUtils.e("SSL configuration failed", e, TAG)
        }
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return getRetrofit().create(serviceClass)
    }

    suspend fun <T> executeCall(block: suspend () -> T): NetworkState<T> {
        return try {
            NetworkState.Success(block())
        } catch (e: Exception) {
            NetworkState.Error(mapException(e))
        }
    }

    private fun mapException(throwable: Throwable): NetworkException {
        return when (throwable) {
            is retrofit2.HttpException -> {
                when (throwable.code()) {
                    401 -> NetworkException.UnauthorizedError()
                    in 400..499 -> NetworkException.HttpError(
                        throwable.code(),
                        throwable.message(),
                        throwable.response()?.errorBody()?.string()
                    )

                    in 500..599 -> NetworkException.ServerError(
                        throwable.code(),
                        throwable.message()
                    )

                    else -> NetworkException.HttpError(
                        throwable.code(),
                        throwable.message()
                    )
                }
            }

            is java.net.SocketTimeoutException -> NetworkException.TimeoutError()
            is java.net.UnknownHostException -> NetworkException.NetworkError(throwable)
            is com.google.gson.JsonParseException -> NetworkException.ParseError(throwable)
            else -> NetworkException.NetworkError(throwable)
        }
    }
}