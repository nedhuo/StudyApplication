package com.example.lib_base.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.lifecycle.LifecycleObserver
import com.example.LibBase.R

abstract class BaseDialog @JvmOverloads constructor(
    context: Context,
    themeResId: Int = R.style.BaseDialogStyle
) : Dialog(context, themeResId), LifecycleObserver {

    protected val mBinding: VDB by lazy(mode = LazyThreadSafetyMode.NONE) {
        getViewBinding(layoutInflater)
    }

    override fun dismiss() {
        super.dismiss()
        mBinding.unbind()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(mBinding.root)
        initStyle(window)
        initView()
        initData()
        initListener()
    }

    open fun initStyle(window: Window?) {
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
    }

    abstract fun initView()
    abstract fun initData()
    open fun initListener() {}
}