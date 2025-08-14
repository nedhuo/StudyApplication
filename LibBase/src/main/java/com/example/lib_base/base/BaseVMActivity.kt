package com.example.lib_base.base

import androidx.lifecycle.ViewModelProvider
import com.example.lib_base.GenericViewModelFactory
import java.lang.reflect.ParameterizedType

abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {

    // 创建ViewModel实例
    protected val viewModel: VM by lazy { ViewModelProvider(this, GenericViewModelFactory { createViewModel() })[viewModelClass] }

    // 获取ViewModel的Class对象
    @Suppress("UNCHECKED_CAST")
    private val viewModelClass: Class<VM> by lazy {
        (javaClass.genericSuperclass as? ParameterizedType)?.actualTypeArguments?.get(0) as? Class<VM>
            ?: throw IllegalStateException("ViewModel type not specified")
    }

    // 允许子类自定义ViewModel创建逻辑
    protected open fun createViewModel(): VM {
        return try {
            viewModelClass.getDeclaredConstructor().newInstance()
        } catch (e: Exception) {
            throw IllegalArgumentException("Failed to create ViewModel. Ensure it has a no-arg constructor or override createViewModel()", e)
        }
    }

}