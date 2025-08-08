package com.example.lib_log

import android.content.Context
import timber.log.Timber

/**
 * 日志管理器
 */
object LogManager {
    var config = LogConfig()
    private var fileLogger: FileLogger? = null
    private var crashReporter = CrashReporter.getInstance()

    fun init(context: Context, config: LogConfig = LogConfig()) {
        this.config = config
        
        if (config.isDebug) {
            Timber.plant(Timber.DebugTree())
        }
        
        if (config.isLogToFile) {
            fileLogger = FileLogger.getInstance(context)
        }

        // 注册未捕获异常处理器
        Thread.setDefaultUncaughtExceptionHandler { _, throwable ->
            e("Crash", throwable.message ?: "Unknown error", throwable)
            if (config.isUploadCrash) {
                crashReporter.reportCrash(throwable)
            }
        }
    }

    fun v(tag: String, message: String) {
        if (config.logLevel.ordinal <= LogLevel.VERBOSE.ordinal) {
            Timber.tag(tag).v(message)
            fileLogger?.log(LogLevel.VERBOSE, tag, message)
        }
    }

    fun d(tag: String, message: String) {
        if (config.logLevel.ordinal <= LogLevel.DEBUG.ordinal) {
            Timber.tag(tag).d(message)
            fileLogger?.log(LogLevel.DEBUG, tag, message)
        }
    }

    fun i(tag: String, message: String) {
        if (config.logLevel.ordinal <= LogLevel.INFO.ordinal) {
            Timber.tag(tag).i(message)
            fileLogger?.log(LogLevel.INFO, tag, message)
        }
    }

    fun w(tag: String, message: String) {
        if (config.logLevel.ordinal <= LogLevel.WARN.ordinal) {
            Timber.tag(tag).w(message)
            fileLogger?.log(LogLevel.WARN, tag, message)
        }
    }

    fun e(tag: String, message: String, throwable: Throwable? = null) {
        if (config.logLevel.ordinal <= LogLevel.ERROR.ordinal) {
            if (throwable != null) {
                Timber.tag(tag).e(throwable, message)
            } else {
                Timber.tag(tag).e(message)
            }
            fileLogger?.log(LogLevel.ERROR, tag, "$message\n${throwable?.stackTraceToString() ?: ""}")
        }
    }
}