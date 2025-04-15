package com.example.lib_monitor.utils

import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.os.SystemClock
import com.example.lib_log.LogUtils
import java.util.concurrent.atomic.AtomicBoolean

/**
 * 主线程卡顿监控
 */
object BlockCanary {
    private const val TAG = "BlockCanary"
    
    // 卡顿阈值（毫秒）
    private const val BLOCK_THRESHOLD_MS = 500L
    
    // 采样间隔（毫秒）
    private const val SAMPLE_INTERVAL_MS = 100L
    
    // 监控线程
    private val monitorThread = HandlerThread("BlockCanaryThread").apply { start() }
    private val monitorHandler = Handler(monitorThread.looper)
    
    // 主线程Handler
    private val mainHandler = Handler(Looper.getMainLooper())
    
    // 是否正在监控
    private val isMonitoring = AtomicBoolean(false)
    
    // 上次采样时间
    private var lastSampleTime = 0L
    
    /**
     * 开始监控
     */
    fun start() {
        if (isMonitoring.get()) return
        isMonitoring.set(true)
        
        // 记录开始监控时间
        lastSampleTime = SystemClock.elapsedRealtime()
        
        // 启动采样
        monitorHandler.post(object : Runnable {
            override fun run() {
                if (!isMonitoring.get()) return
                
                // 发送消息到主线程
                mainHandler.post {
                    // 记录主线程处理该消息的时间
                    val currentTime = SystemClock.elapsedRealtime()
                    val blockTime = currentTime - lastSampleTime
                    
                    // 判断是否卡顿
                    if (blockTime >= BLOCK_THRESHOLD_MS) {
                        val stackTrace = Looper.getMainLooper().thread.stackTrace
                        reportBlock(blockTime, stackTrace)
                    }
                    
                    lastSampleTime = currentTime
                }
                
                // 继续下一次采样
                monitorHandler.postDelayed(this, SAMPLE_INTERVAL_MS)
            }
        })
    }
    
    /**
     * 停止监控
     */
    fun stop() {
        isMonitoring.set(false)
        monitorHandler.removeCallbacksAndMessages(null)
        mainHandler.removeCallbacksAndMessages(null)
    }
    
    /**
     * 报告卡顿信息
     */
    private fun reportBlock(blockTime: Long, stackTrace: Array<StackTraceElement>) {
        val stackTraceString = stackTrace.joinToString("\\n") { element ->
            "    at $element"
        }
        
        LogUtils.w(TAG, """
            发生卡顿！
            卡顿时长：${blockTime}ms
            主线程堆栈：
            $stackTraceString
        """.trimIndent())
    }
}