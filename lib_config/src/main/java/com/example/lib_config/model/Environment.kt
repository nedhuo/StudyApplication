package com.example.lib_config.model

/**
 * 环境配置
 */
enum class Environment(
    val baseUrl: String,
    val wsUrl: String,
    val h5Url: String,
    val isDebug: Boolean,
    val headers: Map<String, String> = emptyMap(),
    val enableCache: Boolean = false,
    val cacheSize: Long = 10 * 1024 * 1024, // 10MB
    val showLog: Boolean = false,
    val trustSSL: Boolean = false
) {
    /**
     * 开发环境
     */
    DEV(
        baseUrl = "https://dev-api.example.com/",
        wsUrl = "wss://dev-ws.example.com/",
        h5Url = "https://dev-h5.example.com/",
        isDebug = true,
        headers = mapOf("Authorization" to "Bearer dev-token"),
        enableCache = true,
        cacheSize = 20 * 1024 * 1024, // 20MB
        showLog = true,
        trustSSL = true
    ),

    /**
     * 预发布环境
     */
    STAGING(
        baseUrl = "https://staging-api.example.com/",
        wsUrl = "wss://staging-ws.example.com/",
        h5Url = "https://staging-h5.example.com/",
        isDebug = true,
        headers = mapOf("Authorization" to "Bearer staging-token"),
        enableCache = true,
        cacheSize = 10 * 1024 * 1024, // 10MB
        showLog = true,
        trustSSL = true
    ),

    /**
     * 生产环境
     */
    PROD(
        baseUrl = "https://api.example.com/",
        wsUrl = "wss://ws.example.com/",
        h5Url = "https://h5.example.com/",
        isDebug = false,
        headers = emptyMap(),
        enableCache = false,
        cacheSize = 10 * 1024 * 1024, // 10MB
        showLog = false,
        trustSSL = false
    )
}