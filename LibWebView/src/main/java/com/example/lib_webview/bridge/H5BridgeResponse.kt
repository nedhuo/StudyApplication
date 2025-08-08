package com.example.lib_webview.bridge

/**
 * H5 桥接响应数据类
 * @param code 响应码
 * @param message 响应消息
 * @param data 响应数据
 */
data class H5BridgeResponse(
    val code: Int = CODE_SUCCESS,  // 响应码，0 表示成功
    val message: String = "",      // 响应消息
    val data: Any? = null         // 响应数据
) {
    companion object {
        const val CODE_SUCCESS = 0      // 成功
        const val CODE_ERROR = -1       // 错误
        const val CODE_TIMEOUT = -2     // 超时
        const val CODE_NOT_FOUND = -3   // 未找到处理器
        const val CODE_CANCEL = -4      // 取消

        fun success(data: Any? = null) = H5BridgeResponse(data = data)
        
        fun error(code: Int, message: String) = H5BridgeResponse(code = code, message = message)
        
        fun error(message: String) = H5BridgeResponse(code = CODE_ERROR, message = message)
    }
} 