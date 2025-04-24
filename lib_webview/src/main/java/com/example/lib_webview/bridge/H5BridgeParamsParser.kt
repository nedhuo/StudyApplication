package com.example.lib_webview.bridge

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import org.json.JSONObject

/**
 * H5 桥接参数解析工具类
 */
object H5BridgeParamsParser {
    private val gson = Gson()

    /**
     * 解析参数为指定类型
     */
    @Throws(JsonSyntaxException::class)
    fun <T> parseParams(params: String, clazz: Class<T>): T {
        return gson.fromJson(params, clazz)
    }

    /**
     * 解析参数为 JSONObject
     */
    @Throws(org.json.JSONException::class)
    fun parseJsonObject(params: String): JSONObject {
        return JSONObject(params)
    }

    /**
     * 安全解析参数为指定类型，失败返回 null
     */
    fun <T> parseParamsSafely(params: String, clazz: Class<T>): T? {
        return try {
            parseParams(params, clazz)
        } catch (e: Exception) {
            null
        }
    }

    /**
     * 安全解析参数为 JSONObject，失败返回空对象
     */
    fun parseJsonObjectSafely(params: String): JSONObject {
        return try {
            parseJsonObject(params)
        } catch (e: Exception) {
            JSONObject()
        }
    }
} 