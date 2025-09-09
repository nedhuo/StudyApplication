package com.example.lib_base.ext

import com.example.lib_base.state.ViewState
import com.nedhuo.libnetwork.result.BaseResponse
import com.nedhuo.libnetwork.result.NetworkResponse
import com.nedhuo.libutils.utilcode.util.ToastUtils


/**
 * 将 NetworkResponse 转换为 ViewState。
 * 转换逻辑可以在这里定制，也可以在具体 ViewModel 中覆盖。
 */
fun <T> NetworkResponse<T>.toViewState(): ViewState<T> = when (this) {
    is NetworkResponse.Success -> {
        // 这里的逻辑可以根据具体业务需求调整，不强制返回 Empty
        ViewState.Success(this.data)
    }

    is NetworkResponse.Error -> ViewState.Error(this.code, this.message)
    is NetworkResponse.Exception -> ViewState.Exception(this.e)
    is NetworkResponse.Loading -> ViewState.Loading
}

/**
 * 对 BaseResponse 进行拦截处理。可以拦截服务器异常单独处理
 */
fun <T> BaseResponse<T>.onIntercepted(interceptor: (BaseResponse<T>) -> Unit): BaseResponse<T> {
    interceptor.invoke(this)
    return this
}

fun <T> BaseResponse<T>.getDataOrThrow(errorHandler: (BaseResponse<T>) -> Boolean): T? {
    if (this.isSuccessful()) {
        return this.data
    } else {
        val intercept = errorHandler.invoke(this)
        if (intercept) return null

        //通用后端错误处理
        ToastUtils.showShort(this.message)
        return null
    }
}