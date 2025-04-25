package com.example.feature_main

import android.app.Application
import com.example.lib_base.BaseApplication

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BaseApplication.init(this)
    }
} 