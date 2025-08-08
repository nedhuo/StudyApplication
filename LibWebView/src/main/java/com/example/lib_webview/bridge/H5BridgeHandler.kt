package com.example.lib_webview.bridge

/**
 * H5 桥接处理器接口
 */
interface H5BridgeHandler {
    /**
     * 处理 H5 调用
     * @param request H5 请求
     * @param callback 回调函数
     */
    fun handle(request: H5BridgeRequest, callback: (H5BridgeResponse) -> Unit)
} 