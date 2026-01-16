package com.example.lib_base.ext

import com.nedhuo.libnetwork.exception.ApiException
import com.nedhuo.libnetwork.result.BaseResponse


fun <T> BaseResponse<T>.dataOrThrow(errorHandler: ((BaseResponse<T>) -> Boolean)? = null): T? {
    if (this.isSuccessful()) {
        return this.data
    } else {
        throw ApiException(code, message)
    }
}