package com.example.lib_webview.bridge

/**
 * WebView JavaScript 回调接口
 */
interface WebViewJavaScriptCallback {
    /**
     * 接收到消息
     */
    fun onMessageReceived(type: String, data: String)
    
    /**
     * 关闭 WebView
     */
    fun onCloseWebView()
    
    /**
     * 分享内容
     */
    fun onShare(title: String, content: String, url: String)
} 