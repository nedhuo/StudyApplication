package com.example.lib_base.base

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.lib_base.GenericViewModelFactory
import com.example.lib_base.ext.dismissLoading
import com.example.lib_base.ext.showLoading
import kotlinx.coroutines.launch
import java.lang.reflect.ParameterizedType

abstract class BaseVMActivity<VM : BaseViewModel> : BaseActivity() {

    protected val viewModel: VM by lazy { ViewModelProvider(this, GenericViewModelFactory { createViewModel() })[viewModelClass] }

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

    override fun initObservers() {
        super.initObservers()

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                viewModel.loadingFlow.collect { loading ->
                    if (loading) showLoading() else dismissLoading()
                }
            }
        }
    }
}