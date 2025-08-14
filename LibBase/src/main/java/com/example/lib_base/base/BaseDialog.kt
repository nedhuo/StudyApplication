package com.example.lib_base.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.LibBase.R

abstract class BaseDialog @JvmOverloads constructor(context: Context, themeResId: Int = R.style.BaseDialogStyle) : Dialog(context, themeResId) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initStyle(window)
        initView()
        initListener()
        initData()
    }

    open fun initStyle(window: Window?) {
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    abstract fun initView()

    open fun initListener() {}

    abstract fun initData()
}