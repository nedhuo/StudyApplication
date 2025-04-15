package com.example.lib_common.startup

import android.content.Context
import androidx.startup.Initializer
import com.example.lib_common.manager.CommonManager

/**
 * 公共组件初始化器
 */
class CommonInitializer : Initializer<Unit> {
    
    override fun create(context: Context) {
        CommonManager.init(context.applicationContext)
    }
    
    override fun dependencies(): List<Class<out Initializer<*>>> {
        // 公共组件依赖日志和网络模块
        return listOf(
            LogInitializer::class.java,
            NetworkInitializer::class.java
        )
    }
}