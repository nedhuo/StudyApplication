package com.example.lib_base.state

/**
 * UI状态封装
 */
sealed class ViewState<out T> {
    data object Loading : ViewState<Nothing>()
    data object Empty : ViewState<Nothing>()
    data class Success<T>(val data: T) : ViewState<T>()
    data class Error(val code: Int, val message: String) : ViewState<Nothing>()
    data class Exception(val throwable: Throwable) : ViewState<Nothing>()

    val isLoading: Boolean get() = this is Loading
    val isEmpty: Boolean get() = this is Empty
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isException: Boolean get() = this is Exception

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    inline fun onLoading(action: () -> Unit): ViewState<T> {
        if (this is Loading) action()
        return this
    }

    inline fun onEmpty(action: () -> Unit): ViewState<T> {
        if (this is Empty) action()
        return this
    }

    inline fun onSuccess(action: (T) -> Unit): ViewState<T> {
        if (this is Success) action(data)
        return this
    }

    inline fun onError(action: (code: Int, message: String) -> Unit): ViewState<T> {
        if (this is Error) action(code, message)
        return this
    }

    inline fun onException(action: (Throwable) -> Unit): ViewState<T> {
        if (this is Exception) action(throwable)
        return this
    }
}