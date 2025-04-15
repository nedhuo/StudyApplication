package com.example.lib_base.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.example.lib_base.utils.ActivityManager
import com.example.lib_log.LogManager
import java.lang.reflect.ParameterizedType

/**
 * Activity基类
 */
abstract class BaseActivity<VB : ViewBinding> : AppCompatActivity() {
    protected lateinit var binding: VB
    private val TAG = this.javaClass.simpleName

    @Suppress("UNCHECKED_CAST")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // 获取ViewBinding
        val type = javaClass.genericSuperclass as ParameterizedType
        val clazz = type.actualTypeArguments[0] as Class<VB>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        binding = method.invoke(null, layoutInflater) as VB
        setContentView(binding.root)
        
        // 添加到Activity管理器
        ActivityManager.addActivity(this)
        
        LogManager.d(TAG, "onCreate")
        
        initView()
        initData()
        initObservers()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.removeActivity(this)
        LogManager.d(TAG, "onDestroy")
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