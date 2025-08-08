package com.example.lib_webview.view

import android.content.Context
import android.util.AttributeSet
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.lib_log.LogManager
import com.example.lib_webview.bridge.H5BridgeManager

/**
 * 基础 WebView
 */
class BaseWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    private var isInjected = false

    init {
        initSettings()
        setupWebViewClient()
        setupBridge()
    }

    /**
     * 初始化设置
     */
    private fun initSettings() {
        with(settings) {
            // 基础设置
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            allowFileAccess = true
            allowContentAccess = true
            
            // 安全设置
            allowFileAccessFromFileURLs = false
            allowUniversalAccessFromFileURLs = false
            
            // UI 设置
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
            loadWithOverviewMode = true
            useWideViewPort = true
            
            // 缓存设置
            cacheMode = WebSettings.LOAD_DEFAULT
        }
    }

    /**
     * 设置 WebViewClient
     */
    private fun setupWebViewClient() {
        webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(view, url)

            }
        }
        
        webChromeClient = object : WebChromeClient() {
            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                super.onProgressChanged(view, newProgress)
                // 可以在这里处理加载进度
            }
        }
    }

    /**
     * 设置桥接
     */
    private fun setupBridge() {
        H5BridgeManager.setWebView(this)
        addJavascriptInterface(H5BridgeManager, "nativeBridge")
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        H5BridgeManager.setWebView(null)
    }

    override fun destroy() {
        H5BridgeManager.setWebView(null)
        super.destroy()
    }
}