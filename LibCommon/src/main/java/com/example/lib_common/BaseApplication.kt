package com.example.lib_common

import android.app.Application
import android.content.Context

object BaseApplication {
    private lateinit var application: Application
    
    fun init(app: Application) {
        application = app
        initSDK()
    }
    
    private fun initSDK() {
        // 初始化各种SDK
    }
    
    fun getApplication(): Application {
        return application
    }
    
    fun getContext(): Context {
        return application.applicationContext
    }
} 