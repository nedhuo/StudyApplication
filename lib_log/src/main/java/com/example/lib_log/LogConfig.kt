package com.example.lib_log

/**
 * 日志配置类
 */
data class LogConfig(
    var isDebug: Boolean = true,                     // 是否为调试模式
    var logLevel: LogLevel = LogLevel.VERBOSE,       // 日志级别
    var tag: String = "AppLog",                      // 默认标签
    var isLogToFile: Boolean = false,                // 是否写入文件
    var isUploadCrash: Boolean = false,             // 是否上传崩溃日志
    var maxLogFileSize: Long = 10 * 1024 * 1024,    // 单个日志文件最大大小（默认10MB）
    var logFilePath: String? = null,                // 日志文件路径
    var crashReportUrl: String? = null              // 崩溃日志上传地址
)