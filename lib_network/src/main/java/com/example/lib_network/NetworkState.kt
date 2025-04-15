package com.example.lib_network

/**
 * 网络请求状态封装
 */
sealed class NetworkState<out T> {
    data class Success<T>(val data: T) : NetworkState<T>()
    data class Error(val error: Throwable) : NetworkState<Nothing>()
    data object Loading : NetworkState<Nothing>()
    
    val isSuccess: Boolean get() = this is Success
    val isError: Boolean get() = this is Error
    val isLoading: Boolean get() = this is Loading
    
    fun getOrNull(): T? = when (this) {
        is Success -> data
        else -> null
    }
    
    fun getOrThrow(): T = when (this) {
        is Success -> data
        is Error -> throw error
        is Loading -> throw IllegalStateException("Loading state")
    }
}