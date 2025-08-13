package com.example.lib_base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
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


    override fun showLoading(content: String?) {

    }

    override fun dismissLoading() {
        TODO("Not yet implemented")
    }
}