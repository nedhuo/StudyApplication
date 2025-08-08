package com.example.lib_log

import android.util.Log
import java.io.File
import java.io.FileWriter
import java.text.SimpleDateFormat
import java.util.*

/**
 * 日志工具类,提供以下功能:
 * 1. 控制台日志打印
 * 2. 文件日志记录
 * 3. 日志级别控制
 * 4. 自定义TAG支持
 * 5. 日志文件自动清理
 */
object LogUtils {

    private const val DEFAULT_TAG = "LogUtils"
    private const val MAX_LOG_FILE_SIZE = 10 * 1024 * 1024 // 10MB
    private const val LOG_FILE_PREFIX = "app_log_"
    private const val LOG_FILE_SUFFIX = ".txt"
    
    // 日志级别
    const val VERBOSE = Log.VERBOSE
    const val DEBUG = Log.DEBUG
    const val INFO = Log.INFO
    const val WARN = Log.WARN
    const val ERROR = Log.ERROR

    // 当前日志级别,默认为DEBUG
    @Volatile
    private var currentLogLevel = DEBUG

    // 是否将日志写入文件
    @Volatile
    private var writeToFile = false

    // 日志文件目录
    private var logDir: File? = null

    // 日期格式化
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())

    /**
     * 初始化日志工具
     * @param logLevel 日志级别
     * @param enableFileLog 是否启用文件日志
     * @param logDirectory 日志文件目录
     */
    fun init(logLevel: Int = DEBUG, enableFileLog: Boolean = false, logDirectory: File? = null) {
        currentLogLevel = logLevel
        writeToFile = enableFileLog
        logDir = logDirectory
        if (writeToFile && logDir != null) {
            if (!logDir!!.exists()) {
                logDir!!.mkdirs()
            }
            cleanOldLogs()
        }
    }

    fun v(msg: String, tag: String = DEFAULT_TAG) = log(VERBOSE, tag, msg)
    fun d(msg: String, tag: String = DEFAULT_TAG) = log(DEBUG, tag, msg)
    fun i(msg: String, tag: String = DEFAULT_TAG) = log(INFO, tag, msg)
    fun w(msg: String, tag: String = DEFAULT_TAG) = log(WARN, tag, msg)
    fun e(msg: String, tag: String = DEFAULT_TAG) = log(ERROR, tag, msg)
    fun e(msg: String, throwable: Throwable, tag: String = DEFAULT_TAG) {
        log(ERROR, tag, "$msg\n${Log.getStackTraceString(throwable)}")
    }

    /**
     * 记录日志
     */
    private fun log(level: Int, tag: String, msg: String) {
        if (level < currentLogLevel) return

        // 打印到控制台
        when (level) {
            VERBOSE -> Log.v(tag, msg)
            DEBUG -> Log.d(tag, msg)
            INFO -> Log.i(tag, msg)
            WARN -> Log.w(tag, msg)
            ERROR -> Log.e(tag, msg)
        }

        // 写入文件
        if (writeToFile && logDir != null) {
            writeLogToFile(level, tag, msg)
        }
    }

    /**
     * 将日志写入文件
     */
    private fun writeLogToFile(level: Int, tag: String, msg: String) {
        try {
            val logFile = getLogFile()
            val timestamp = dateFormat.format(Date())
            val levelStr = when (level) {
                VERBOSE -> "V"
                DEBUG -> "D"
                INFO -> "I"
                WARN -> "W"
                ERROR -> "E"
                else -> "?"
            }
            
            val logEntry = "$timestamp $levelStr/$tag: $msg\n"
            FileWriter(logFile, true).use { writer ->
                writer.append(logEntry)
            }
        } catch (e: Exception) {
            Log.e(DEFAULT_TAG, "Failed to write log to file", e)
        }
    }

    /**
     * 获取当前日志文件
     */
    private fun getLogFile(): File {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        val fileName = "$LOG_FILE_PREFIX$today$LOG_FILE_SUFFIX"
        return File(logDir, fileName)
    }

    /**
     * 清理旧的日志文件
     */
    private fun cleanOldLogs() {
        try {
            val files = logDir?.listFiles { file ->
                file.name.startsWith(LOG_FILE_PREFIX) && file.name.endsWith(LOG_FILE_SUFFIX)
            } ?: return

            // 按修改时间排序
            val sortedFiles = files.sortedBy { it.lastModified() }
            
            // 删除超过大小限制的旧文件
            var totalSize = 0L
            for (file in sortedFiles) {
                totalSize += file.length()
                if (totalSize > MAX_LOG_FILE_SIZE) {
                    file.delete()
                }
            }
        } catch (e: Exception) {
            Log.e(DEFAULT_TAG, "Failed to clean old logs", e)
        }
    }

    /**
     * 设置日志级别
     */
    fun setLogLevel(level: Int) {
        currentLogLevel = level
    }

    /**
     * 启用/禁用文件日志
     */
    fun setWriteToFile(enable: Boolean) {
        writeToFile = enable
    }

    /**
     * 获取所有日志文件
     */
    fun getLogFiles(): Array<File>? = logDir?.listFiles { file ->
        file.name.startsWith(LOG_FILE_PREFIX) && file.name.endsWith(LOG_FILE_SUFFIX)
    }

    /**
     * 删除所有日志文件
     */
    fun clearAllLogs() {
        getLogFiles()?.forEach { it.delete() }
    }
}