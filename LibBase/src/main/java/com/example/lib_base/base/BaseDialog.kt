package com.example.lib_base.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import com.example.LibBase.R
import com.example.lib_base.ui.LoadingDialog

abstract class BaseDialog @JvmOverloads constructor(context: Context, themeResId: Int = R.style.BaseDialogStyle) : Dialog(context, themeResId), IStateView {

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

    override fun onDetachedFromWindow() {
        loadingDialog?.dismissLoading()
        super.onDetachedFromWindow()
    }

    /**************************Loading****************************/

    private var loadingDialog: LoadingDialog? = null

    override fun showLoading(content: String?) {
        if (loadingDialog == null) loadingDialog = LoadingDialog()
        loadingDialog?.showLoading(content)
    }

    override fun dismissLoading() {
        loadingDialog?.dismissLoading()
        loadingDialog = null
    }
}