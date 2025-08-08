package com.example.lib_log.ext

import com.example.lib_log.LogManager
import kotlin.reflect.KClass

/**
 * 日志扩展函数
 */

// 为Any类型添加扩展函数，方便任何类都能直接调用日志方法
fun Any.logV(message: String) {
    LogManager.v(this.javaClass.simpleName, message)
}

fun Any.logD(message: String) {
    LogManager.d(this.javaClass.simpleName, message)
}

fun Any.logI(message: String) {
    LogManager.i(this.javaClass.simpleName, message)
}

fun Any.logW(message: String) {
    LogManager.w(this.javaClass.simpleName, message)
}

fun Any.logE(message: String, throwable: Throwable? = null) {
    LogManager.e(this.javaClass.simpleName, message, throwable)
}

// 为异常类添加扩展函数，方便直接打印异常
fun Throwable.log(message: String = this.message ?: "Unknown error") {
    LogManager.e("Exception", message, this)
}

// 为集合类型添加扩展函数，方便打印集合内容
fun <T> Collection<T>.logList(tag: String? = null) {
    val actualTag = tag ?: "Collection"
    LogManager.d(actualTag, "Size: ${this.size}")
    this.forEachIndexed { index, item ->
        LogManager.d(actualTag, "[$index] = $item")
    }
}

// 为Map类型添加扩展函数
fun <K, V> Map<K, V>.logMap(tag: String? = null) {
    val actualTag = tag ?: "Map"
    LogManager.d(actualTag, "Size: ${this.size}")
    this.forEach { (key, value) ->
        LogManager.d(actualTag, "[$key] = $value")
    }
}

// 为对象添加扩展函数，方便打印对象属性
inline fun <reified T : Any> T.logObject(tag: String? = null) {
    val actualTag = tag ?: this::class.java.simpleName
    LogManager.d(actualTag, toString())
}

// 为函数执行添加日志跟踪
inline fun <T> logMethod(tag: String? = null, block: () -> T): T {
    val methodName = Thread.currentThread().stackTrace[1].methodName
    val actualTag = tag ?: methodName
    LogManager.d(actualTag, "⭐️ Method Start: $methodName")
    val startTime = System.currentTimeMillis()
    try {
        val result = block()
        val endTime = System.currentTimeMillis()
        LogManager.d(actualTag, "✅ Method End: $methodName, Time: ${endTime - startTime}ms")
        return result
    } catch (e: Exception) {
        val endTime = System.currentTimeMillis()
        LogManager.e(actualTag, "❌ Method Error: $methodName, Time: ${endTime - startTime}ms", e)
        throw e
    }
}

// 为Boolean类型添加条件日志
fun Boolean.logIf(tag: String, trueMessage: String, falseMessage: String) {
    if (this) {
        LogManager.d(tag, trueMessage)
    } else {
        LogManager.d(tag, falseMessage)
    }
}

// 为可空类型添加日志扩展
fun <T : Any> T?.logNullable(tag: String? = null) {
    val actualTag = tag ?: "Nullable"
    if (this == null) {
        LogManager.w(actualTag, "Value is null")
    } else {
        LogManager.d(actualTag, "Value: $this")
    }
}

// 为数字类型添加性能日志
fun Long.logPerformance(tag: String, operation: String) {
    val time = System.currentTimeMillis() - this
    LogManager.d(tag, "⏱ $operation took ${time}ms")
}

// 为网络请求添加日志
object NetworkLog {
    fun request(url: String, method: String, headers: Map<String, String>? = null, body: Any? = null) {
        LogManager.d("Network", """
            🌐 Request:
            URL: $url
            Method: $method
            Headers: ${headers?.toString() ?: "None"}
            Body: ${body?.toString() ?: "None"}
        """.trimIndent())
    }

    fun response(url: String, code: Int, body: Any?, time: Long) {
        LogManager.d("Network", """
            📥 Response:
            URL: $url
            Code: $code
            Time: ${time}ms
            Body: ${body?.toString() ?: "None"}
        """.trimIndent())
    }

    fun error(url: String, error: Throwable) {
        LogManager.e("Network", """
            ❌ Error:
            URL: $url
            Error: ${error.message}
        """.trimIndent(), error)
    }
}

// 为生命周期事件添加日志
object LifecycleLog {
    fun create(className: String) {
        LogManager.d("Lifecycle", "📱 $className.onCreate()")
    }

    fun start(className: String) {
        LogManager.d("Lifecycle", "▶️ $className.onStart()")
    }

    fun resume(className: String) {
        LogManager.d("Lifecycle", "⏯ $className.onResume()")
    }

    fun pause(className: String) {
        LogManager.d("Lifecycle", "⏸ $className.onPause()")
    }

    fun stop(className: String) {
        LogManager.d("Lifecycle", "⏹ $className.onStop()")
    }

    fun destroy(className: String) {
        LogManager.d("Lifecycle", "💀 $className.onDestroy()")
    }
}