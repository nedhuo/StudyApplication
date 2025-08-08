package com.example.lib_webview.bridge

import android.webkit.JavascriptInterface
import com.example.lib_log.LogManager
import com.google.gson.Gson
import org.json.JSONObject

/**
 * WebView JavaScript 桥接类
 */
class WebViewJavaScriptBridge(private val callback: WebViewJavaScriptCallback) {
    private val gson = Gson()
    
    /**
     * 发送消息到原生
     */
    @JavascriptInterface
    fun postMessage(message: String) {
        try {
            val jsonObject = JSONObject(message)
            val type = jsonObject.optString("type")
            val data = jsonObject.optString("data")
            
            callback.onMessageReceived(type, data)
        } catch (e: Exception) {
            LogManager.e("WebViewJavaScriptBridge", "处理消息失败: $message", e)
        }
    }
    
    /**
     * 获取设备信息
     */
    @JavascriptInterface
    fun getDeviceInfo(): String {
        return try {
            val deviceInfo = mapOf(
                "platform" to "android",
                "version" to android.os.Build.VERSION.RELEASE,
                "model" to android.os.Build.MODEL,
                "manufacturer" to android.os.Build.MANUFACTURER
            )
            gson.toJson(deviceInfo)
        } catch (e: Exception) {
            LogManager.e("WebViewJavaScriptBridge", "获取设备信息失败", e)
            "{}"
        }
    }
    
    /**
     * 关闭 WebView
     */
    @JavascriptInterface
    fun closeWebView() {
        callback.onCloseWebView()
    }
    
    /**
     * 分享内容
     */
    @JavascriptInterface
    fun share(title: String, content: String, url: String) {
        callback.onShare(title, content, url)
    }
} 