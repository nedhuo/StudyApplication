package com.example.lib_webview.cookie

import android.content.Context
import android.os.Build
import android.webkit.CookieManager
import android.webkit.CookieSyncManager
import com.example.lib_log.LogManager
import java.net.HttpCookie
import java.net.URI

/**
 * WebView Cookie 管理器
 */
object WebViewCookieManager {
    private const val TAG = "WebViewCookieManager"
    
    /**
     * 初始化 Cookie 管理器
     */
    fun init(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(context)
        }
        // 允许接受第三方 Cookie
        CookieManager.getInstance().setAcceptThirdPartyCookies(null, true)
    }
    
    /**
     * 设置 Cookie
     */
    fun setCookie(url: String, cookie: String) {
        try {
            CookieManager.getInstance().setCookie(url, cookie)
            sync()
        } catch (e: Exception) {
            LogManager.e(TAG, "设置 Cookie 失败: url=$url, cookie=$cookie", e)
        }
    }
    
    /**
     * 设置多个 Cookie
     */
    fun setCookies(url: String, cookies: List<String>) {
        cookies.forEach { cookie ->
            setCookie(url, cookie)
        }
    }
    
    /**
     * 设置 HttpCookie
     */
    fun setHttpCookie(url: String, cookie: HttpCookie) {
        val cookieString = "${cookie.name}=${cookie.value}; " +
            "Domain=${cookie.domain}; " +
            "Path=${cookie.path}; " +
            (if (cookie.secure) "Secure; " else "") +
            (if (cookie.isHttpOnly) "HttpOnly; " else "") +
            if (cookie.maxAge > 0) "Max-Age=${cookie.maxAge}; " else ""
        setCookie(url, cookieString)
    }
    
    /**
     * 获取指定 URL 的所有 Cookie
     */
    fun getCookie(url: String): String? {
        return try {
            CookieManager.getInstance().getCookie(url)
        } catch (e: Exception) {
            LogManager.e(TAG, "获取 Cookie 失败: url=$url", e)
            null
        }
    }
    
    /**
     * 获取指定 URL 的所有 Cookie 列表
     */
    fun getCookieList(url: String): List<HttpCookie> {
        val cookieList = mutableListOf<HttpCookie>()
        try {
            val cookieString = getCookie(url) ?: return emptyList()
            val uri = URI(url)
            cookieString.split(";").forEach { cookie ->
                try {
                    HttpCookie.parse(cookie.trim()).forEach { httpCookie ->
                        httpCookie.domain = uri.host
                        cookieList.add(httpCookie)
                    }
                } catch (e: Exception) {
                    LogManager.e(TAG, "解析 Cookie 失败: cookie=$cookie", e)
                }
            }
        } catch (e: Exception) {
            LogManager.e(TAG, "获取 Cookie 列表失败: url=$url", e)
        }
        return cookieList
    }
    
    /**
     * 移除指定 URL 的所有 Cookie
     */
    fun removeCookie(url: String) {
        try {
            val cookieManager = CookieManager.getInstance()
            val cookie = cookieManager.getCookie(url)
            if (cookie != null) {
                val cookies = cookie.split(";")
                cookies.forEach { item ->
                    val cookieParts = item.split("=")
                    if (cookieParts.size >= 1) {
                        val newCookie = "${cookieParts[0].trim()}=; expires=Thu, 01-Jan-1970 00:00:01 GMT"
                        cookieManager.setCookie(url, newCookie)
                    }
                }
                sync()
            }
        } catch (e: Exception) {
            LogManager.e(TAG, "移除 Cookie 失败: url=$url", e)
        }
    }
    
    /**
     * 移除所有 Cookie
     */
    fun removeAllCookies() {
        try {
            CookieManager.getInstance().removeAllCookies { success ->
                LogManager.d(TAG, "移除所有 Cookie ${if (success) "成功" else "失败"}")
            }
            sync()
        } catch (e: Exception) {
            LogManager.e(TAG, "移除所有 Cookie 失败", e)
        }
    }
    
    /**
     * 同步 Cookie
     */
    fun sync() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            CookieManager.getInstance().flush()
        } else {
            CookieSyncManager.getInstance().sync()
        }
    }
} 