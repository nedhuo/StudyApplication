package com.example.lib_config.core

import android.content.Context
import android.content.SharedPreferences
import com.example.lib_config.model.Environment
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

/**
 * 应用配置管理类
 */
object AppConfig {
    private const val SP_NAME = "app_config"
    private const val KEY_ENVIRONMENT = "current_environment"
    
    private lateinit var sp: SharedPreferences
    private lateinit var applicationContext: Context
    
    // 当前环境配置
    private val _currentEnvironment = MutableStateFlow(Environment.PROD)
    val currentEnvironment: StateFlow<Environment> = _currentEnvironment
    
    /**
     * 初始化配置
     */
    fun init(context: Context) {
        applicationContext = context.applicationContext
        sp = applicationContext.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE)
        
        // 读取保存的环境配置
        val savedEnvironment = sp.getString(KEY_ENVIRONMENT, null)
        if (savedEnvironment != null) {
            try {
                _currentEnvironment.value = Environment.valueOf(savedEnvironment)
            } catch (e: Exception) {
                _currentEnvironment.value = Environment.PROD
            }
        }
    }
    
    /**
     * 切换环境
     */
    fun switchEnvironment(environment: Environment) {
        _currentEnvironment.value = environment
        sp.edit().putString(KEY_ENVIRONMENT, environment.name).apply()
    }
    
    /**
     * 获取当前 API 基础 URL
     */
    fun getBaseUrl(): String = currentEnvironment.value.baseUrl
    
    /**
     * 获取当前 WebSocket URL
     */
    fun getWsUrl(): String = currentEnvironment.value.wsUrl
    
    /**
     * 获取当前 H5 URL
     */
    fun getH5Url(): String = currentEnvironment.value.h5Url
    
    /**
     * 是否为调试模式
     */
    fun isDebug(): Boolean = currentEnvironment.value.isDebug

    /**
     * 获取当前请求头
     */
    fun getHeaders(): Map<String, String>? = currentEnvironment.value.headers

    /**
     * 是否启用缓存
     */
    fun isEnableCache(): Boolean = currentEnvironment.value.enableCache

    /**
     * 获取缓存大小
     */
    fun getCacheSize(): Long = currentEnvironment.value.cacheSize

    /**
     * 是否显示日志
     */
    fun isShowLog(): Boolean = currentEnvironment.value.showLog

    /**
     * 是否信任所有 SSL
     */
    fun isTrustSSL(): Boolean = currentEnvironment.value.trustSSL
}