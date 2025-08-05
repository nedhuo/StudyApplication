package com.example.lib_network.result

/**
 * 基础响应模型
 */
data class BaseResponse<T>(
    val code: Int = 0,
    val message: String = "",
    val data: T? = null
) {
    val isSuccess: Boolean
        get() = code == 0

    fun toResult(): NetworkResponse<T> = when {
        isSuccess && data != null -> NetworkResponse.Success(data)
        else -> NetworkResponse.Error(code, message)
    }
}