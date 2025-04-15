package com.example.lib_network.result

/**
 * 网络请求结果包装类
 */
sealed class Result<out T> {
    data class Success<T>(val data: T) : Result<T>()
    data class Error(val code: Int, val message: String) : Result<Nothing>()
    data class Exception(val e: Throwable) : Result<Nothing>()
    data object Loading : Result<Nothing>()

    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isException: Boolean get() = this is Exception
    val isLoading: Boolean get() = this is Loading

    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }

    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw RuntimeException("API Error: $code - $message")
        is Exception -> throw e
        is Loading -> throw IllegalStateException("Result is Loading")
    }

    fun onSuccess(action: (T) -> Unit): Result<T> {
        if (this is Success) action(data)
        return this
    }

    fun onError(action: (code: Int, message: String) -> Unit): Result<T> {
        if (this is Error) action(code, message)
        return this
    }

    fun onException(action: (Throwable) -> Unit): Result<T> {
        if (this is Exception) action(e)
        return this
    }

    fun onLoading(action: () -> Unit): Result<T> {
        if (this is Loading) action()
        return this
    }
}