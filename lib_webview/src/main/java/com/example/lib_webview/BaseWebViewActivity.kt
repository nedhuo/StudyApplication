package com.example.lib_webview

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.view.ViewGroup
import android.webkit.*
import android.widget.FrameLayout
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import com.example.lib_log.LogManager
import com.example.lib_webview.bridge.WebViewJavaScriptBridge
import com.example.lib_webview.bridge.WebViewJavaScriptCallback

/**
 * 基础 WebView Activity
 */
abstract class BaseWebViewActivity : AppCompatActivity(), WebViewJavaScriptCallback {
    private var webViewContainer: FrameLayout? = null
    private var webView: WebView? = null
    private var progressBar: ProgressBar? = null
    private var filePathCallback: ValueCallback<Array<Uri>>? = null
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initWebView()
    }
    
    /**
     * 初始化 WebView
     */
    @SuppressLint("InflateParams", "AddJavascriptInterface")
    private fun initWebView() {
        try {
            // 初始化容器
            webViewContainer = FrameLayout(this).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            }
            setContentView(webViewContainer)
            
            // 初始化进度条
            progressBar = layoutInflater.inflate(R.layout.layout_web_progress, null) as ProgressBar
            webViewContainer?.addView(progressBar)
            
            // 初始化 WebView
            WebViewManager.init(this)
            webView = WebViewManager.getWebView()
            webView?.let { webView ->
                // 添加 JavaScript 接口
                webView.addJavascriptInterface(WebViewJavaScriptBridge(this), "Android")
                
                // 设置 WebViewClient
                webView.webViewClient = object : WebViewClient() {
                    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
                        // 处理自定义 URL scheme
                        if (url.startsWith("tel:") || url.startsWith("mailto:") || 
                            url.startsWith("geo:") || url.startsWith("whatsapp:")) {
                            try {
                                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                                return true
                            } catch (e: Exception) {
                                LogManager.e("BaseWebViewActivity", "无法处理 URL: $url", e)
                            }
                        }
                        view.loadUrl(url)
                        return true
                    }
                    
                    override fun onPageFinished(view: WebView?, url: String?) {
                        super.onPageFinished(view, url)
                        onWebViewPageFinished(url)
                    }
                    
                    override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
                        super.onReceivedError(view, request, error)
                        onWebViewError(error)
                    }
                    
                    override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
                        onWebViewSslError(handler, error)
                    }
                }
                
                // 设置 WebChromeClient
                webView.webChromeClient = object : WebChromeClient() {
                    override fun onProgressChanged(view: WebView, newProgress: Int) {
                        progressBar?.progress = newProgress
                        progressBar?.visibility = if (newProgress < 100) View.VISIBLE else View.GONE
                        onWebViewProgressChanged(newProgress)
                    }
                    
                    override fun onReceivedTitle(view: WebView, title: String) {
                        onWebViewTitleReceived(title)
                    }
                    
                    override fun onShowFileChooser(
                        webView: WebView,
                        filePathCallback: ValueCallback<Array<Uri>>,
                        fileChooserParams: FileChooserParams
                    ): Boolean {
                        this@BaseWebViewActivity.filePathCallback = filePathCallback
                        startActivityForResult(fileChooserParams.createIntent(), FILE_CHOOSER_REQUEST_CODE)
                        return true
                    }
                }
                
                // 添加到容器
                webViewContainer?.addView(webView)
                
                // 加载初始 URL
                val url = getInitialUrl()
                if (url.isNotEmpty()) {
                    webView.loadUrl(url)
                }
            }
        } catch (e: Exception) {
            LogManager.e("BaseWebViewActivity", "初始化 WebView 失败", e)
        }
    }
    
    /**
     * 获取初始 URL
     */
    abstract fun getInitialUrl(): String
    
    /**
     * WebView 加载进度变化回调
     */
    open fun onWebViewProgressChanged(progress: Int) {}
    
    /**
     * WebView 标题接收回调
     */
    open fun onWebViewTitleReceived(title: String) {}
    
    /**
     * WebView 页面加载完成回调
     */
    open fun onWebViewPageFinished(url: String?) {}
    
    /**
     * WebView 错误回调
     */
    open fun onWebViewError(error: WebResourceError?) {}
    
    /**
     * WebView SSL 错误回调
     */
    open fun onWebViewSslError(handler: SslErrorHandler?, error: SslError?) {
        // 默认取消加载
        handler?.cancel()
    }
    
    /**
     * 执行 JavaScript 代码
     */
    fun evaluateJavaScript(script: String, resultCallback: ((String?) -> Unit)? = null) {
        webView?.evaluateJavascript(script, resultCallback)
    }
    
    /**
     * 处理返回键
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webView?.canGoBack() == true) {
            webView?.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILE_CHOOSER_REQUEST_CODE) {
            filePathCallback?.onReceiveValue(
                if (resultCode == RESULT_OK && data != null)
                    WebChromeClient.FileChooserParams.parseResult(resultCode, data)
                else
                    null
            )
            filePathCallback = null
        }
    }
    
    override fun onDestroy() {
        WebViewManager.destroy()
        super.onDestroy()
    }
    
    /**
     * JavaScript 回调：接收到消息
     */
    override fun onMessageReceived(type: String, data: String) {
        // 子类实现具体逻辑
    }
    
    /**
     * JavaScript 回调：关闭 WebView
     */
    override fun onCloseWebView() {
        finish()
    }
    
    /**
     * JavaScript 回调：分享内容
     */
    override fun onShare(title: String, content: String, url: String) {
        // 子类实现具体逻辑
    }
    
    companion object {
        private const val FILE_CHOOSER_REQUEST_CODE = 1001
    }
} 