package com.example.lib_webview

import android.content.Context
import android.webkit.WebView
import com.nedhuo.log.LogManager
import com.example.lib_webview.cookie.WebViewCookieManager

/**
 * WebView 管理器
 */
object WebViewManager {
    private var webView: WebView? = null
    
    /**
     * 初始化 WebView
     */
    fun init(context: Context) {
        try {
            if (webView == null) {
                // 初始化 Cookie 管理器
                WebViewCookieManager.init(context)
                
                webView = WebView(context.applicationContext)
                configWebView()
            }
        } catch (e: Exception) {
            LogManager.e("WebViewManager", "初始化 WebView 失败", e)
        }
    }
    
    /**
     * 配置 WebView
     */
    private fun configWebView() {
        webView?.apply {
            settings.apply {
                // 基本设置
                javaScriptEnabled = WebViewConfig.isJavaScriptEnabled
                domStorageEnabled = WebViewConfig.isDomStorageEnabled
                databaseEnabled = WebViewConfig.isDatabaseEnabled
                cacheMode = WebViewConfig.cacheMode
                
                // 缩放设置
                setSupportZoom(WebViewConfig.isSupportZoom)
                builtInZoomControls = WebViewConfig.isSupportZoom
                displayZoomControls = WebViewConfig.isDisplayZoomControls
                
                // 窗口设置
                setSupportMultipleWindows(WebViewConfig.isSupportMultipleWindows)
                
                // 编码设置
                defaultTextEncodingName = WebViewConfig.defaultTextEncodingName
                
                // 图片加载
                loadsImagesAutomatically = WebViewConfig.isLoadImagesAutomatically
                
                // 混合内容
                mixedContentMode = WebViewConfig.mixedContentMode
                
                // 用户代理
                WebViewConfig.userAgentString?.let { userAgentString = it }
                
                // 文件访问
                allowFileAccess = WebViewConfig.isAllowFileAccess
                allowContentAccess = WebViewConfig.isAllowContentAccess
                allowFileAccessFromFileURLs = WebViewConfig.isAllowFileAccessFromFileURLs
                allowUniversalAccessFromFileURLs = WebViewConfig.isAllowUniversalAccessFromFileURLs
            }
        }
    }
    
    /**
     * 加载 URL
     */
    fun loadUrl(url: String) {
        webView?.loadUrl(url)
    }
    
    /**
     * 加载 URL（带 Cookie）
     */
    fun loadUrlWithCookie(url: String, cookie: String) {
        WebViewCookieManager.setCookie(url, cookie)
        loadUrl(url)
    }
    
    /**
     * 加载 URL（带多个 Cookie）
     */
    fun loadUrlWithCookies(url: String, cookies: List<String>) {
        WebViewCookieManager.setCookies(url, cookies)
        loadUrl(url)
    }
    
    /**
     * 销毁 WebView
     */
    fun destroy() {
        webView?.apply {
            loadUrl("about:blank")
            clearHistory()
            destroy()
        }
        webView = null
    }
    
    /**
     * 清除缓存
     */
    fun clearCache() {
        webView?.apply {
            clearCache(true)
            clearFormData()
            clearHistory()
            clearSslPreferences()
        }
    }
    
    /**
     * 清除所有数据（包括 Cookie）
     */
    fun clearAll() {
        clearCache()
        WebViewCookieManager.removeAllCookies()
    }
    
    /**
     * 获取 WebView 实例
     */
    fun getWebView(): WebView? = webView
    
    /**
     * 重新加载配置
     */
    fun reloadConfig() {
        configWebView()
    }
} 