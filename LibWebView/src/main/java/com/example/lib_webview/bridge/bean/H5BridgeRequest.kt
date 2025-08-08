package com.example.lib_webview.bridge.bean

/**
 * H5 桥接请求数据类
 * @param action 请求动作
 * @param params 请求参数（JSON 格式）
 * @param callback 回调函数名称
 */
data class H5BridgeRequest(
    val action: String,           // 请求动作
    val params: String,           // 请求参数（JSON 格式）
    val callback: String? = null  // 回调函数名称
) 