package com.nedhuo.libnetwork

import android.content.Context

/**
 * 网络配置管理器
 */
object NetworkConfigManager {
    private var config: NetworkConfig = NetworkConfig()

    /**
     * 获取当前网络配置
     */
    fun getConfig(): NetworkConfig {
        return config
    }

    /**
     * 设置网络配置
     */
    fun setConfig(newConfig: NetworkConfig) {
        config = newConfig
    }

    /**
     * 初始化网络配置
     */
    fun init(context: Context, initialConfig: NetworkConfig = NetworkConfig()) {
        config = initialConfig
    }
}