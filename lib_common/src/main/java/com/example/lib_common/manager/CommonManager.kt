package com.example.lib_common.manager

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

/**
 * 公共组件管理类
 */
@SuppressLint("StaticFieldLeak")
object CommonManager {
    private lateinit var context: Context
    
    fun init(context: Context) {
        this.context = context
    }
    
    fun getContext(): Context = context
}