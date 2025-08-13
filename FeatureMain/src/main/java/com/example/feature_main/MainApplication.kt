package com.example.feature_main

import android.app.Application

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        BaseApplication.init(this)
    }
} 