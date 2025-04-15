package com.example.lib_monitor.utils

import android.app.ActivityManager
import android.content.Context
import android.os.FileObserver
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import com.example.lib_log.LogUtils
import java.io.File
import java.io.RandomAccessFile
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.atomic.AtomicBoolean

/**
 * ANR 监控工具
 * 通过监控 /data/anr/traces.txt 文件变化来检测 ANR
 */
object AnrMonitor {
    private const val TAG = "AnrMonitor"
    private const val ANR_TRIGGER_TIME = 5000L // 5秒
    
    private lateinit var context: Context
    private var anrObserver: FileObserver? = null
    private val isMonitoring = AtomicBoolean(false)
    private val mainHandler = Handler(Looper.getMainLooper())
    private var lastAnrTime = 0L
    
    // 主线程检测
    private val mainThreadChecker = object : Runnable {
        override fun run() {
            checkMainThread()
            if (isMonitoring.get()) {
                mainHandler.postDelayed(this, 1000)
            }
        }
    }
    
    /**
     * 初始化 ANR 监控
     */
    fun init(context: Context) {
        this.context = context.applicationContext
    }
    
    /**
     * 开始监控
     */
    fun start() {
        if (isMonitoring.get()) return
        isMonitoring.set(true)
        
        // 启动主线程监控
        mainHandler.post(mainThreadChecker)
        
        // 监控 ANR 文件
        try {
            val anrFile = File("/data/anr/traces.txt")
            if (anrFile.exists()) {
                anrObserver = object : FileObserver(anrFile.absolutePath) {
                    override fun onEvent(event: Int, path: String?) {
                        if (event == MODIFY || event == CLOSE_WRITE) {
                            onAnrDetected()
                        }
                    }
                }.apply { startWatching() }
            }
        } catch (e: Exception) {
            LogUtils.e(TAG, "Failed to start ANR file observer", e)
        }
    }
    
    /**
     * 停止监控
     */
    fun stop() {
        if (!isMonitoring.get()) return
        isMonitoring.set(false)
        
        mainHandler.removeCallbacks(mainThreadChecker)
        anrObserver?.stopWatching()
        anrObserver = null
    }
    
    /**
     * 检查主线程状态
     */
    private fun checkMainThread() {
        val startTime = SystemClock.elapsedRealtime()
        val checkRunnable = object : Runnable {
            override fun run() {
                val endTime = SystemClock.elapsedRealtime()
                val delay = endTime - startTime
                if (delay >= ANR_TRIGGER_TIME) {
                    onMainThreadBlocked(delay)
                }
            }
        }
        
        mainHandler.post(checkRunnable)
    }
    
    /**
     * 主线程阻塞回调
     */
    private fun onMainThreadBlocked(blockTime: Long) {
        val currentTime = System.currentTimeMillis()
        // 避免重复报告
        if (currentTime - lastAnrTime < ANR_TRIGGER_TIME) return
        lastAnrTime = currentTime
        
        val stackTrace = Looper.getMainLooper().thread.stackTrace
        val processInfo = getProcessInfo()
        
        LogUtils.e(TAG, """
            检测到主线程阻塞！
            阻塞时长：${blockTime}ms
            进程信息：
            ${processInfo}
            
            主线程堆栈：
            ${stackTrace.joinToString("\\n")}
        """.trimIndent())
    }
    
    /**
     * ANR 文件变化回调
     */
    private fun onAnrDetected() {
        val currentTime = System.currentTimeMillis()
        // 避免重复报告
        if (currentTime - lastAnrTime < ANR_TRIGGER_TIME) return
        lastAnrTime = currentTime
        
        try {
            val anrFile = File("/data/anr/traces.txt")
            if (anrFile.exists()) {
                val traces = RandomAccessFile(anrFile, "r").use { file ->
                    val length = file.length()
                    val position = maxOf(0, length - 50 * 1024) // 读取最后 50KB
                    file.seek(position)
                    file.readBytes().toString(Charsets.UTF_8)
                }
                
                val processInfo = getProcessInfo()
                LogUtils.e(TAG, """
                    检测到 ANR！
                    发生时间：${SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS", Locale.getDefault()).format(Date())}
                    进程信息：
                    ${processInfo}
                    
                    ANR 堆栈：
                    $traces
                """.trimIndent())
            }
        } catch (e: Exception) {
            LogUtils.e(TAG, "Failed to read ANR traces", e)
        }
    }
    
    /**
     * 获取进程信息
     */
    private fun getProcessInfo(): String {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val pid = android.os.Process.myPid()
        val processInfo = am.runningAppProcesses?.find { it.pid == pid }
        
        return """
            PID: $pid
            进程名: ${processInfo?.processName}
            重要性: ${processInfo?.importance}
            内存占用: ${getProcessMemorySize()}
        """.trimIndent()
    }
    
    /**
     * 获取进程内存占用
     */
    private fun getProcessMemorySize(): String {
        val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val pid = android.os.Process.myPid()
        val pids = intArrayOf(pid)
        val memoryInfo = am.getProcessMemoryInfo(pids)
        
        return if (memoryInfo.isNotEmpty()) {
            "${memoryInfo[0].totalPss / 1024} MB"
        } else {
            "Unknown"
        }
    }
}