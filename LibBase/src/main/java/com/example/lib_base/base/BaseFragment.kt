package com.example.lib_base.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.lib_log.LogManager

/**
 * Fragment base class, no ViewBinding reflection.
 * Subclasses should handle their own ViewBinding.
 */
abstract class BaseFragment : Fragment() {
    private val TAG: String = this.javaClass.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogManager.d(TAG, "onViewCreated")
        initArguments(arguments)
        initView()
        initData()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogManager.d(TAG, "onDestroyView")
    }

    protected open fun initArguments(arguments: Bundle?) {}

    /**
     * Initialize view
     */
    protected abstract fun initView()

    /**
     * Initialize data
     */
    protected abstract fun initData()

    /**
     * Initialize observers
     */
    protected open fun initObservers() {}
}