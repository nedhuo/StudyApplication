package com.example.lib_network

/**
 * 网络配置类
 */
data class NetworkConfig(
    var baseUrl: String = "", // API基础URL
    var timeout: Long = 30L, // 超时时间（秒）
    var retryCount: Int = 0, // 重试次数
    var showLog: Boolean = false, // 是否显示日志
    var headers: Map<String, String> = emptyMap(), // 全局请求头
    var cacheSize: Long = 10 * 1024 * 1024, // 缓存大小（默认10MB）
    var enableCache: Boolean = true, // 是否启用缓存
    var trustSSL: Boolean = false // 是否信任所有SSL证书
)