package com.example.lib_monitor.utils

import android.app.ActivityManager
import android.content.Context
import android.os.Debug
import android.os.Process
import java.io.BufferedReader
import java.io.FileReader

/**
 * 内存监控工具类
 */
object MemoryUtils {
    
    /**
     * 获取内存信息
     */
    fun getMemoryInfo(context: Context): MemoryStatus {
        val activityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val memoryInfo = ActivityManager.MemoryInfo()
        activityManager.getMemoryInfo(memoryInfo)
        
        // 获取应用内存信息
        val nativeHeapSize = Debug.getNativeHeapSize()
        val nativeHeapAllocated = Debug.getNativeHeapAllocatedSize()
        val nativeHeapFree = Debug.getNativeHeapFreeSize()
        
        // 获取 PSS 内存信息
        val pss = getPssInfo()
        
        return MemoryStatus(
            // 应用内存
            nativeHeapSize = nativeHeapSize,
            nativeHeapAllocated = nativeHeapAllocated,
            nativeHeapFree = nativeHeapFree,
            dalvikPss = pss.dalvikPss,
            nativePss = pss.nativePss,
            totalPss = pss.totalPss,
            
            // 系统内存
            totalMemory = memoryInfo.totalMem,
            availableMemory = memoryInfo.availMem,
            lowMemory = memoryInfo.lowMemory,
            lowMemoryThreshold = memoryInfo.threshold,
            
            // 内存使用率
            memoryUsagePercent = ((memoryInfo.totalMem - memoryInfo.availMem) * 100f / memoryInfo.totalMem)
        )
    }
    
    /**
     * 获取 PSS 内存信息
     */
    private fun getPssInfo(): PssInfo {
        var dalvikPss = 0L
        var nativePss = 0L
        var totalPss = 0L
        
        try {
            val pid = Process.myPid()
            val smapsFile = FileReader("/proc/$pid/smaps")
            val reader = BufferedReader(smapsFile)
            var line: String?
            
            while (reader.readLine().also { line = it } != null) {
                if (line?.startsWith("Pss:") == true) {
                    val pss = line?.split("\\s+".toRegex())?.get(1)?.toLong() ?: 0
                    totalPss += pss
                    
                    // 检查上一行来确定是 Dalvik 还是 Native 堆
                    when {
                        line?.contains("dalvik", ignoreCase = true) == true -> dalvikPss += pss
                        line?.contains("native", ignoreCase = true) == true -> nativePss += pss
                    }
                }
            }
            reader.close()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        
        return PssInfo(dalvikPss, nativePss, totalPss)
    }
}

/**
 * 内存状态数据类
 */
data class MemoryStatus(
    // 应用内存
    val nativeHeapSize: Long,         // Native 堆大小
    val nativeHeapAllocated: Long,    // Native 堆已分配大小
    val nativeHeapFree: Long,         // Native 堆剩余大小
    val dalvikPss: Long,             // Dalvik 堆 PSS
    val nativePss: Long,             // Native 堆 PSS
    val totalPss: Long,              // 总 PSS
    
    // 系统内存
    val totalMemory: Long,           // 系统总内存
    val availableMemory: Long,       // 系统可用内存
    val lowMemory: Boolean,          // 是否处于低内存状态
    val lowMemoryThreshold: Long,    // 低内存阈值
    
    // 内存使用率
    val memoryUsagePercent: Float    // 内存使用率(%)
)

/**
 * PSS 内存信息数据类
 */
private data class PssInfo(
    val dalvikPss: Long,    // Dalvik 堆 PSS
    val nativePss: Long,    // Native 堆 PSS
    val totalPss: Long      // 总 PSS
)