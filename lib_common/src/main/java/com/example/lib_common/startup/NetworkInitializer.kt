package com.example.lib_common.startup

import android.content.Context
import androidx.startup.Initializer
import com.example.lib_network.NetworkManager

/**
 * 网络初始化器
 */
class NetworkInitializer : Initializer<Unit> {
    
    override fun create(context: Context) {
        NetworkManager.init(context)
    }
    
    override fun dependencies(): List<Class<out Initializer<*>>> {
        // 网络模块依赖日志模块
        return listOf(LogInitializer::class.java)
    }
}