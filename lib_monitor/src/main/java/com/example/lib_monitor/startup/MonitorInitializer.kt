package com.example.lib_monitor.startup

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.example.lib_monitor.core.AppMonitor

/**
 * 性能监控初始化器
 */
class MonitorInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        AppMonitor.init(context.applicationContext as Application)
    }
    
    override fun dependencies(): List<Class<out Initializer<*>>> {
        return emptyList()
    }
}