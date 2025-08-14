package com.example.lib_base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lib_base.ui.LoadingDialog
import com.example.lib_base.utils.ActivityManager
import com.example.lib_log.LogManager

/**
 * Activity base class, no ViewBinding reflection.
 * Subclasses should handle their own ViewBinding.
 */
abstract class BaseActivity : AppCompatActivity(), IStateView {
    private val TAG: String = this.javaClass.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityManager.addActivity(this)
        LogManager.d(TAG, "onCreate")
        initView()
        initListener()
        initObservers()
        initData()
    }

    override fun onDestroy() {
        super.onDestroy()
        loadingDialog?.dismissLoading()
        ActivityManager.removeActivity(this)
        LogManager.d(TAG, "onDestroy")
    }

    /**
     * Initialize view
     */
    protected abstract fun initView()


    protected open fun initListener() {}

    /**
     * Initialize observers
     */
    protected open fun initObservers() {}

    /**
     * Initialize data
     */
    protected abstract fun initData()


    /**************************Loading****************************/

    private var loadingDialog: LoadingDialog? = null

    override fun showLoading(content: String?) {
        if (loadingDialog == null) loadingDialog = LoadingDialog(this)
        loadingDialog?.showLoading(content)
    }

    override fun dismissLoading() {
        loadingDialog?.dismissLoading()
        loadingDialog = null
    }
}