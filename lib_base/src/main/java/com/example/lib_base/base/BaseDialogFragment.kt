package com.example.lib_base.base

import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.lib_log.LogManager

/**
 * DialogFragment base class, no ViewBinding reflection.
 * Subclasses should handle their own ViewBinding.
 */
abstract class BaseDialogFragment : DialogFragment() {
    private val TAG: String = this.javaClass.simpleName

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogManager.d(TAG, "onViewCreated")
        dialog?.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent)
            setWindowAnimations(android.R.style.Animation_Dialog)
            setLayout(
                (context.resources.displayMetrics.widthPixels * 0.8).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        initView()
        initData()
        initObservers()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        LogManager.d(TAG, "onDestroyView")
    }

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