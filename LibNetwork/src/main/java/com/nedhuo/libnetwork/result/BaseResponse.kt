package com.nedhuo.libnetwork.result

data class BaseResponse<T>(
    val code: Int? = null,
    val message: String? = null,
    val data: T? = null,
) {

    fun isSuccessful(): Boolean {
        return code == 200
    }
}