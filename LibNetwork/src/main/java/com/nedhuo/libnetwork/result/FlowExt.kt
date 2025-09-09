package com.nedhuo.libnetwork.result

import com.nedhuo.log.LogManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onStart
import retrofit2.HttpException
import java.io.IOException

/**
 * 将网络请求转换为Flow
 */
fun <T> apiCall(call: suspend () -> T): Flow<NetworkResponse<T>> = flow {
    try {
        emit(NetworkResponse.Success(call()))
    } catch (e: Exception) {
        when (e) {
            is HttpException -> {
                emit(NetworkResponse.Error(e.code(), e.message()))
                LogManager.e("Network", "HTTP Error: ${e.code()} - ${e.message()}")
            }
            is IOException -> {
                emit(NetworkResponse.Exception(e))
                LogManager.e("Network", "IO Exception: ${e.message}")
            }
            else -> {
                emit(NetworkResponse.Exception(e))
                LogManager.e("Network", "Unknown Error: ${e.message}")
            }
        }
    }
}.onStart {
    emit(NetworkResponse.Loading)
}.catch { e ->
    emit(NetworkResponse.Exception(e))
    LogManager.e("Network", "Flow Exception: ${e.message}")
}

/**
 * 将网络请求转换为Flow，带有数据转换
 */
fun <T, R> apiCall(call: suspend () -> T, transform: (T) -> R): Flow<NetworkResponse<R>> = flow {
    try {
        emit(NetworkResponse.Success(transform(call())))
    } catch (e: Exception) {
        when (e) {
            is HttpException -> {
                emit(NetworkResponse.Error(e.code(), e.message()))
                LogManager.e("Network", "HTTP Error: ${e.code()} - ${e.message()}")
            }
            is IOException -> {
                emit(NetworkResponse.Exception(e))
                LogManager.e("Network", "IO Exception: ${e.message}")
            }
            else -> {
                emit(NetworkResponse.Exception(e))
                LogManager.e("Network", "Unknown Error: ${e.message}")
            }
        }
    }
}.onStart {
    emit(NetworkResponse.Loading)
}.catch { e ->
    emit(NetworkResponse.Exception(e))
    LogManager.e("Network", "Flow Exception: ${e.message}")
}