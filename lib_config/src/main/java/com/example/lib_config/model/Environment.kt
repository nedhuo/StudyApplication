package com.example.lib_config.model

/**
 * 环境配置
 */
enum class Environment(
    val baseUrl: String,
    val wsUrl: String,
    val h5Url: String,
    val isDebug: Boolean
) {
    /**
     * 开发环境
     */
    DEV(
        baseUrl = "https://dev-api.example.com/",
        wsUrl = "wss://dev-ws.example.com/",
        h5Url = "https://dev-h5.example.com/",
        isDebug = true
    ),

    /**
     * 预发布环境
     */
    STAGING(
        baseUrl = "https://staging-api.example.com/",
        wsUrl = "wss://staging-ws.example.com/",
        h5Url = "https://staging-h5.example.com/",
        isDebug = true
    ),

    /**
     * 生产环境
     */
    PROD(
        baseUrl = "https://api.example.com/",
        wsUrl = "wss://ws.example.com/",
        h5Url = "https://h5.example.com/",
        isDebug = false
    )
}