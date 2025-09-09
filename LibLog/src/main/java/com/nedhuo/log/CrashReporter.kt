package com.nedhuo.log

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONObject
import java.io.IOException
import java.util.concurrent.TimeUnit

/**
 * 崩溃日志上报器
 */
class CrashReporter private constructor() {
    private val client = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .writeTimeout(15, TimeUnit.SECONDS)
        .readTimeout(15, TimeUnit.SECONDS)
        .build()

    fun reportCrash(throwable: Throwable) {
        LogManager.config.crashReportUrl?.let { url ->
            val crashInfo = JSONObject().apply {
                put("timestamp", System.currentTimeMillis())
                put("message", throwable.message ?: "Unknown error")
                put("stackTrace", throwable.stackTraceToString())
            }

            val request = Request.Builder()
                .url(url)
                .post(crashInfo.toString().toRequestBody("application/json".toMediaType()))
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    LogManager.e("CrashReporter", "Failed to upload crash report", e)
                }

                override fun onResponse(call: Call, response: Response) {
                    response.close()
                }
            })
        }
    }

    companion object {
        @Volatile
        private var instance: CrashReporter? = null

        fun getInstance(): CrashReporter {
            return instance ?: synchronized(this) {
                instance ?: CrashReporter().also { instance = it }
            }
        }
    }
}