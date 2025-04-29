package com.example.lib_spider.meowtv.spider

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor

object MeowTvSpider {
    suspend fun fetchMeowTvSitesJson(url: String): String {
        return withContext(Dispatchers.IO) {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BODY
            val client = OkHttpClient.Builder()
                .addInterceptor(logging)
                .addInterceptor { chain ->
                    val request = chain.request().newBuilder()
                        .header("User-Agent", "Mozilla/5.0 (Android) AppleWebKit/537.36 Chrome/89.0.4389.90 Mobile Safari/537.36")
                        .build()
                    chain.proceed(request)
                }
                .build()
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            response.body?.string() ?: ""
        }
    }
} 