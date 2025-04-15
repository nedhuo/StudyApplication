package com.example.lib_monitor.utils

import android.view.Choreographer
import com.example.lib_log.LogUtils
import java.util.concurrent.TimeUnit

/**
 * FPS 监控工具
 */
object FpsMonitor {
    private const val TAG = "FpsMonitor"
    
    // 统计周期（毫秒）
    private const val MONITOR_INTERVAL_MS = 1000L
    
    // FPS 警告阈值
    private const val FPS_WARN_THRESHOLD = 45
    
    // 帧数统计
    private var frameCount = 0
    private var lastFrameTime = 0L
    
    // 是否正在监控
    private var isMonitoring = false
    
    // 帧率回调
    private val frameCallback = object : Choreographer.FrameCallback {
        override fun doFrame(frameTimeNanos: Long) {
            if (!isMonitoring) return
            
            val currentFrameTime = TimeUnit.NANOSECONDS.toMillis(frameTimeNanos)
            frameCount++
            
            // 每秒计算一次FPS
            if (currentFrameTime - lastFrameTime >= MONITOR_INTERVAL_MS) {
                val fps = frameCount * 1000 / (currentFrameTime - lastFrameTime)
                onFpsCalculated(fps)
                
                frameCount = 0
                lastFrameTime = currentFrameTime
            }
            
            // 注册下一帧回调
            Choreographer.getInstance().postFrameCallback(this)
        }
    }
    
    /**
     * 开始监控
     */
    fun start() {
        if (isMonitoring) return
        isMonitoring = true
        
        frameCount = 0
        lastFrameTime = TimeUnit.NANOSECONDS.toMillis(System.nanoTime())
        
        Choreographer.getInstance().postFrameCallback(frameCallback)
        LogUtils.d(TAG, "FPS监控已启动")
    }
    
    /**
     * 停止监控
     */
    fun stop() {
        if (!isMonitoring) return
        isMonitoring = false
        
        Choreographer.getInstance().removeFrameCallback(frameCallback)
        LogUtils.d(TAG, "FPS监控已停止")
    }
    
    /**
     * FPS 计算回调
     */
    private fun onFpsCalculated(fps: Long) {
        if (fps <= FPS_WARN_THRESHOLD) {
            LogUtils.w(TAG, "当前帧率过低: ${fps}fps")
        } else {
            LogUtils.v(TAG, "当前帧率: ${fps}fps")
        }
    }
}