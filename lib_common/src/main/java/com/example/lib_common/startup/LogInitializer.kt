package com.example.lib_common.startup

import android.content.Context
import androidx.startup.Initializer
import com.example.lib_log.LogManager

/**
 * 日志初始化器
 */
class LogInitializer : Initializer<Unit> {
    
    override fun create(context: Context) {
        LogManager.init(context, isDebug = true)
    }
    
    override fun dependencies(): List<Class<out Initializer<*>>> {
        // 日志模块没有依赖其他模块
        return emptyList()
    }
}