package com.example.lib_network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 请求头拦截器
 */
class HeaderInterceptor(private val headers: Map<String, String>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()
        val requestBuilder = originalRequest.newBuilder()
        
        headers.forEach { (key, value) ->
            requestBuilder.header(key, value)
        }
        
        return chain.proceed(requestBuilder.build())
    }
}