package com.example.lib_webview.bridge.handlers

import com.example.lib_webview.bridge.H5BridgeHandler
import com.example.lib_webview.bridge.bean.H5BridgeRequest
import com.example.lib_webview.bridge.bean.H5BridgeResponse
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName

/**
 * 分享处理器
 */
class ShareHandler : H5BridgeHandler {
    private val gson = Gson()

    override fun handle(request: H5BridgeRequest, callback: (H5BridgeResponse) -> Unit) {
        try {
            val params = gson.fromJson(request.params, ShareParams::class.java)
            
            // TODO: 实现实际的分享逻辑
            // 这里只是示例，实际应该调用分享 SDK
            
            callback(H5BridgeResponse(
                code = H5BridgeResponse.CODE_SUCCESS,
                message = "分享成功",
                data = mapOf(
                    "platform" to params.platform,
                    "shared" to true
                )
            ))
        } catch (e: Exception) {
            callback(H5BridgeResponse(
                code = H5BridgeResponse.CODE_ERROR,
                message = "分享失败：${e.message}"
            ))
        }
    }

    /**
     * 分享参数
     */
    private data class ShareParams(
        @SerializedName("title")
        val title: String,
        
        @SerializedName("desc")
        val description: String,
        
        @SerializedName("link")
        val link: String,
        
        @SerializedName("image")
        val imageUrl: String? = null,
        
        @SerializedName("platform")
        val platform: String = "wechat" // 默认微信
    )
} 