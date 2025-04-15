package com.example.lib_common.manager

import android.app.Application
import android.content.Context

/**
 * 公共组件管理类
 */
object CommonManager {
    private lateinit var application: Application
    
    fun init(application: Application) {
        this.application = application
    }
    
    fun getContext(): Context = application
}