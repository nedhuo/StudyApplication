package com.example.lib_log

import android.content.Context
import android.util.Log
import java.io.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

/**
 * 文件日志记录器
 */
class FileLogger private constructor(context: Context) {
    private val logExecutor: ExecutorService = Executors.newSingleThreadExecutor()
    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault())
    private var logFile: File
    private var currentFileSize: Long = 0

    init {
        val logDir = File(context.getExternalFilesDir(null), "logs")
        if (!logDir.exists()) {
            logDir.mkdirs()
        }
        logFile = createLogFile(logDir)
    }

    private fun createLogFile(logDir: File): File {
        val timestamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        return File(logDir, "log_$timestamp.txt")
    }

    fun log(level: LogLevel, tag: String, message: String) {
        logExecutor.execute {
            try {
                val timestamp = dateFormat.format(Date())
                val logMessage = "$timestamp ${level.name}/$tag: $message\n"

                if (currentFileSize + logMessage.length > LogManager.config.maxLogFileSize) {
                    rotateLogFile()
                }

                FileOutputStream(logFile, true).bufferedWriter().use { writer ->
                    writer.write(logMessage)
                    currentFileSize += logMessage.length
                }
            } catch (e: IOException) {
                Log.e("FileLogger", "Failed to write log", e)
            }
        }
    }

    private fun rotateLogFile() {
        val logDir = logFile.parentFile
        logFile = createLogFile(logDir!!)
        currentFileSize = 0
    }

    companion object {
        @Volatile
        private var instance: FileLogger? = null

        fun getInstance(context: Context): FileLogger {
            return instance ?: synchronized(this) {
                instance ?: FileLogger(context).also { instance = it }
            }
        }
    }
}