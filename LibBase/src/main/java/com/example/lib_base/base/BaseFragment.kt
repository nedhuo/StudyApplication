package com.example.lib_base.base

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.lib_base.ui.LoadingDialog
import com.nedhuo.log.LogManager

/**
 * Fragment base class, no ViewBinding reflection.
 * Subclasses should handle their own ViewBinding.
 */
abstract class BaseFragment : Fragment(), IStateView  {
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


    /**************************Loading****************************/

    private var loadingDialog: LoadingDialog? = null

    override fun showLoading(content: String?) {
        activity?.let {
            if (loadingDialog == null) loadingDialog = LoadingDialog(it)
            loadingDialog?.showLoading(content)
        }
    }

    override fun dismissLoading() {
        loadingDialog?.dismissLoading()
        loadingDialog = null
    }
}