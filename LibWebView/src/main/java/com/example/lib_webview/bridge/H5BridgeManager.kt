package com.example.lib_webview.bridge

import android.os.Handler
import android.os.Looper
import android.webkit.JavascriptInterface
import android.webkit.WebView
import com.nedhuo.log.LogManager
import com.example.lib_webview.bridge.bean.H5BridgeRequest
import com.example.lib_webview.bridge.bean.H5BridgeResponse
import com.google.gson.Gson
import java.util.concurrent.ConcurrentHashMap
import java.lang.ref.WeakReference

/**
 * H5 桥接管理器
 */
object H5BridgeManager {
    private const val TAG = "H5BridgeManager"
    private const val TIMEOUT_MS = 30000L // 默认超时时间 30s
    
    private val mainHandler = Handler(Looper.getMainLooper())
    private val gson = Gson()
    private val handlers = ConcurrentHashMap<String, H5BridgeHandler>()
    private val pendingCallbacks = ConcurrentHashMap<String, H5CallbackWrapper>()
    private var webViewRef = WeakReference<WebView>(null)
    
    /**
     * 设置当前 WebView
     */
    fun setWebView(webView: WebView?) {
        webViewRef = WeakReference(webView)
    }
    
    /**
     * 注册处理器
     */
    fun registerHandler(name: String, handler: H5BridgeHandler) {
        handlers[name] = handler
    }
    
    /**
     * 注销处理器
     */
    fun unregisterHandler(name: String) {
        handlers.remove(name)
    }
    
    /**
     * 处理 H5 调用
     */
    @JavascriptInterface
    fun handleCall(action: String, params: String, callback: String?) {
        mainHandler.post {
            try {
                val handler = handlers[action] ?: run {
                    handleError(callback, "未找到处理器：$action", H5BridgeResponse.CODE_NOT_FOUND)
                    return@post
                }
                
                val request = H5BridgeRequest(action, params, callback)
                
                if (callback != null) {
                    setupCallback(callback) { response ->
                        handleResponse(callback, response)
                    }
                }
                
                handler.handle(request) { response ->
                    pendingCallbacks[callback]?.callback?.invoke(response) 
                        ?: handleResponse(callback, response)
                }
            } catch (e: Exception) {
                LogManager.e(TAG, "处理 H5 调用失败", e)
                handleError(callback, e.message ?: "未知错误")
            }
        }
    }
    
    /**
     * 设置回调
     */
    private fun setupCallback(callback: String, onResponse: (H5BridgeResponse) -> Unit) {
        val timeoutRunnable = Runnable {
            handleError(callback, "请求超时", H5BridgeResponse.CODE_TIMEOUT)
            pendingCallbacks.remove(callback)
        }
        
        val wrapper = H5CallbackWrapper { response ->
            mainHandler.removeCallbacks(timeoutRunnable)
            onResponse(response)
            pendingCallbacks.remove(callback)
        }
        
        pendingCallbacks[callback] = wrapper
        mainHandler.postDelayed(timeoutRunnable, TIMEOUT_MS)
    }
    
    /**
     * 处理响应
     */
    private fun handleResponse(callback: String?, response: H5BridgeResponse) {
        if (callback == null) return
        
        try {
            val jsonResponse = gson.toJson(response)
            val script = "javascript:window['$callback']($jsonResponse)"
            webViewRef.get()?.evaluateJavascript(script, null)
        } catch (e: Exception) {
            LogManager.e(TAG, "处理响应失败", e)
        }
    }
    
    /**
     * 处理错误
     */
    private fun handleError(callback: String?, message: String, code: Int = H5BridgeResponse.CODE_ERROR) {
        handleResponse(callback, H5BridgeResponse(code = code, message = message))
    }
    
    /**
     * 调用 H5 方法
     */
    fun callH5(action: String, params: Any? = null, callback: ((H5BridgeResponse) -> Unit)? = null) {
        val webView = webViewRef.get() ?: run {
            callback?.invoke(
                H5BridgeResponse(
                    code = H5BridgeResponse.CODE_ERROR,
                    message = "WebView 已释放"
                )
            )
            return
        }
        
        try {
            val jsonParams = if (params != null) gson.toJson(params) else "{}"
            val callbackName = if (callback != null) {
                setupH5Callback(webView, callback)
            } else null
            
            val script = """
                javascript:window.h5Bridge.onNativeCall(
                    '$action',
                    $jsonParams,
                    ${if (callbackName != null) "'$callbackName'" else "null"}
                );
            """.trimIndent()
            
            mainHandler.post {
                webView.evaluateJavascript(script, null)
            }
        } catch (e: Exception) {
            LogManager.e(TAG, "调用 H5 方法失败", e)
            callback?.invoke(
                H5BridgeResponse(
                    code = H5BridgeResponse.CODE_ERROR,
                    message = e.message ?: "未知错误"
                )
            )
        }
    }
    
    /**
     * 设置 H5 回调
     */
    private fun setupH5Callback(webView: WebView, callback: (H5BridgeResponse) -> Unit): String {
        val callbackName = "h5_callback_${System.currentTimeMillis()}"
        
        val timeoutRunnable = Runnable {
            callback.invoke(
                H5BridgeResponse(
                    code = H5BridgeResponse.CODE_TIMEOUT,
                    message = "请求超时"
                )
            )
            pendingCallbacks.remove(callbackName)
        }
        
        pendingCallbacks[callbackName] = H5CallbackWrapper(callback)
        mainHandler.postDelayed(timeoutRunnable, TIMEOUT_MS)
        
        val script = """
            window['$callbackName'] = function(response) {
                delete window['$callbackName'];
                ${callback.javaClass.name}.onCallback(response);
            }
        """.trimIndent()
        webView.evaluateJavascript(script, null)
        
        return callbackName
    }
    
    /**
     * 清理资源
     */
    fun clear() {
        handlers.clear()
        pendingCallbacks.clear()
        mainHandler.removeCallbacksAndMessages(null)
        webViewRef.clear()
    }
    
    private data class H5CallbackWrapper(
        val callback: (H5BridgeResponse) -> Unit
    )
} 