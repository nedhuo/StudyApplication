package com.example.lib_base.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

/**
 * ViewModel基类
 */
abstract class BaseViewModel : ViewModel() {

    // 使用 MutableSharedFlow 发送 UI 加载状态事件
    private val _loadingFlow = MutableSharedFlow<Boolean>()
    val loadingFlow = _loadingFlow.asSharedFlow()

    /**
     * 在 viewModelScope 中安全地启动一个协程，并捕获非取消异常。
     * @param block 协程体
     * @param onError 异常处理回调
     */
    protected fun launchWithLoading(
        showLoading: Boolean = true,
        onCompletion: (() -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null,
        block: suspend () -> Unit,
    ) {
        viewModelScope.launch {
            try {
                if (showLoading) {
                    _loadingFlow.emit(true)
                }
                block()
            } catch (e: Throwable) {
                onError?.invoke(e)
            } finally {
                if (showLoading) {
                    _loadingFlow.emit(false)
                }
                onCompletion?.invoke()
            }
        }
    }
}