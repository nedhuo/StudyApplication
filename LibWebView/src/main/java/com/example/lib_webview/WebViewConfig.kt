package com.example.lib_webview

/**
 * WebView 配置类
 */
object WebViewConfig {
    // 是否启用 JavaScript
    var isJavaScriptEnabled = true
    
    // 是否启用 DOM storage
    var isDomStorageEnabled = true
    
    // 是否启用数据库
    var isDatabaseEnabled = true
    
    // 是否启用应用缓存
    var isAppCacheEnabled = true
    
    // 缓存模式
    var cacheMode = android.webkit.WebSettings.LOAD_DEFAULT
    
    // 是否支持缩放
    var isSupportZoom = true
    
    // 是否显示缩放控件
    var isDisplayZoomControls = false
    
    // 是否支持多窗口
    var isSupportMultipleWindows = true
    
    // 默认文本编码
    var defaultTextEncodingName = "utf-8"
    
    // 是否自动加载图片
    var isLoadImagesAutomatically = true
    
    // 混合内容模式
    var mixedContentMode = android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
    
    // 用户代理字符串
    var userAgentString: String? = null
    
    // 是否允许文件访问
    var isAllowFileAccess = true
    
    // 是否允许内容访问
    var isAllowContentAccess = true
    
    // 是否允许文件 URL 访问其他文件 URL
    var isAllowFileAccessFromFileURLs = false
    
    // 是否允许通用文件 URL 访问所有文件 URL
    var isAllowUniversalAccessFromFileURLs = false
} 