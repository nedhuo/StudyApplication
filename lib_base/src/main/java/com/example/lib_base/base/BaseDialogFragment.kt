package com.example.lib_base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import androidx.viewbinding.ViewBinding
import com.example.lib_log.LogManager
import java.lang.reflect.ParameterizedType

/**
 * DialogFragment基类
 */
abstract class BaseDialogFragment<VB : ViewBinding> : DialogFragment() {
    protected lateinit var binding: VB
    private val TAG = this.javaClass.simpleName

    @Suppress("UNCHECKED_CAST")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 获取ViewBinding
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java)
        binding = method.invoke(null, inflater, container, false) as VB
        
        LogManager.d(TAG, "onCreateView")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        LogManager.d(TAG, "onViewCreated")
        
        dialog?.window?.apply {
            // 设置背景透明
            setBackgroundDrawableResource(android.R.color.transparent)
            // 设置动画
            setWindowAnimations(android.R.style.Animation_Dialog)
            // 设置宽度为屏幕的0.8
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
     * 初始化视图
     */
    protected abstract fun initView()

    /**
     * 初始化数据
     */
    protected abstract fun initData()

    /**
     * 初始化观察者
     */
    protected open fun initObservers() {}
}