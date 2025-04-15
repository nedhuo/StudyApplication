package com.example.lib_base.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_base.state.ViewState
import com.example.lib_log.ext.log
import com.example.lib_network.result.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

/**
 * ViewModel基类
 */
abstract class BaseViewModel : ViewModel() {

    /**
     * 启动协程
     */
    protected fun launchFlow(
        context: CoroutineContext = EmptyCoroutineContext,
        block: suspend () -> Unit
    ) {
        viewModelScope.launch(context) {
            try {
                block()
            } catch (e: Exception) {
                if (e !is CancellationException) {
                    e.log("Coroutine Error")
                }
            }
        }
    }

    /**
     * 处理网络请求（自动处理生命周期和异常）
     */
    protected fun <T> Flow<Result<T>>.handleRequest(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        listenerState: MutableStateFlow<ViewState<T>>? = null,
        listener: (ViewState<T>) -> Unit = {},
        showLoading: Boolean = true,
        transform: (T) -> T = { it }
    ) {
        launchFlow {
            this.flowOn(dispatcher)
                .onStart { 
                    if (showLoading) {
                        listenerState?.value = ViewState.Loading
                        listener(ViewState.Loading)
                    }
                }
                .catch { e ->
                    e.log("Request Error")
                    val state = ViewState.Exception(e)
                    listenerState?.value = state
                    listener(state)
                }
                .collect { result ->
                    val state = when (result) {
                        is Result.Success -> {
                            val transformedData = transform(result.data)
                            if (transformedData == null || (transformedData is List<*> && transformedData.isEmpty())) {
                                ViewState.Empty
                            } else {
                                ViewState.Success(transformedData)
                            }
                        }
                        is Result.Error -> ViewState.Error(result.code, result.message)
                        is Result.Exception -> ViewState.Exception(result.e)
                        is Result.Loading -> ViewState.Loading
                    }
                    listenerState?.value = state
                    listener(state)
                }
        }
    }

    /**
     * 转换Result为ViewState
     */
    protected fun <T> Result<T>.toViewState(): ViewState<T> = when (this) {
        is Result.Success -> {
            if (data == null || (data is List<*> && data.isEmpty())) {
                ViewState.Empty
            } else {
                ViewState.Success(data)
            }
        }
        is Result.Error -> ViewState.Error(code, message)
        is Result.Exception -> ViewState.Exception(e)
        is Result.Loading -> ViewState.Loading
    }

    /**
     * 转换Flow<Result>为Flow<ViewState>
     */
    protected fun <T> Flow<Result<T>>.toViewStateFlow(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        showLoading: Boolean = true,
        transform: (T) -> T = { it }
    ): Flow<ViewState<T>> = this
        .flowOn(dispatcher)
        .onStart { if (showLoading) emit(Result.Loading) }
        .catch { emit(Result.Exception(it)) }
        .map { result ->
            when (result) {
                is Result.Success -> {
                    val transformedData = transform(result.data)
                    if (transformedData == null || (transformedData is List<*> && transformedData.isEmpty())) {
                        ViewState.Empty
                    } else {
                        ViewState.Success(transformedData)
                    }
                }
                is Result.Error -> ViewState.Error(result.code, result.message)
                is Result.Exception -> ViewState.Exception(result.e)
                is Result.Loading -> ViewState.Loading
            }
        }

    /**
     * 创建状态Flow
     */
    protected fun <T> createStateFlow(
        initialValue: ViewState<T> = ViewState.Loading
    ): MutableStateFlow<ViewState<T>> = MutableStateFlow(initialValue)
}

/**
 * 带有单个状态的ViewModel基类
 */
abstract class BaseStateViewModel<T>(initialValue: ViewState<T> = ViewState.Loading) : BaseViewModel() {
    
    protected val _viewState = createStateFlow(initialValue)
    val viewState: StateFlow<ViewState<T>> = _viewState

    protected fun updateState(state: ViewState<T>) {
        _viewState.value = state
    }

    protected fun updateStateWithResult(result: Result<T>) {
        _viewState.value = result.toViewState()
    }
}

/**
 * 带有多个状态的ViewModel基类
 */
abstract class BaseMultiStateViewModel : BaseViewModel() {
    
    private val stateMap = mutableMapOf<String, MutableStateFlow<ViewState<*>>>()

    protected fun <T> getStateFlow(
        key: String,
        initialValue: ViewState<T> = ViewState.Loading
    ): StateFlow<ViewState<T>> {
        @Suppress("UNCHECKED_CAST")
        return stateMap.getOrPut(key) { createStateFlow(initialValue) } as StateFlow<ViewState<T>>
    }

    protected fun <T> updateState(key: String, state: ViewState<T>) {
        @Suppress("UNCHECKED_CAST")
        (stateMap[key] as? MutableStateFlow<ViewState<T>>)?.value = state
    }

    protected fun <T> updateStateWithResult(key: String, result: Result<T>) {
        updateState(key, result.toViewState())
    }
}