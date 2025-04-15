package com.example.lib_monitor.utils

import android.os.Process
import java.io.BufferedReader
import java.io.FileReader
import java.io.IOException

/**
 * CPU 监控工具类
 */
object CpuUtils {
    private const val TAG = "CpuUtils"
    private const val SYSTEM_CPU_FORMAT = "/proc/stat"
    private const val APP_CPU_FORMAT = "/proc/%d/stat"
    
    /**
     * 获取 CPU 信息
     */
    fun getCpuInfo(): CpuInfo {
        return CpuInfo(
            appCpuUsage = getAppCpuUsage(),
            systemCpuUsage = getSystemCpuUsage(),
            cpuFrequency = getCpuFrequency(),
            cpuTemperature = getCpuTemperature(),
            cpuCoreCount = getCpuCores()
        )
    }
    
    /**
     * 获取系统 CPU 使用率
     */
    private fun getSystemCpuUsage(): Float {
        try {
            return readSystemCpuUsage()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0f
    }
    
    /**
     * 获取应用 CPU 使用率
     */
    private fun getAppCpuUsage(): Float {
        try {
            return readAppCpuUsage()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0f
    }
    
    /**
     * 获取 CPU 频率
     */
    private fun getCpuFrequency(): Long {
        try {
            val reader = BufferedReader(FileReader("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq"))
            val freq = reader.readLine().toLong()
            reader.close()
            return freq
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0
    }
    
    /**
     * 获取 CPU 温度
     */
    private fun getCpuTemperature(): Float {
        try {
            val reader = BufferedReader(FileReader("/sys/class/thermal/thermal_zone0/temp"))
            val temp = reader.readLine().toFloat() / 1000
            reader.close()
            return temp
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0f
    }
    
    /**
     * 获取 CPU 核心数
     */
    private fun getCpuCores(): Int {
        return Runtime.getRuntime().availableProcessors()
    }
    
    private fun readSystemCpuUsage(): Float {
        var user: Long = 0
        var nice: Long = 0
        var system: Long = 0
        var idle: Long = 0
        var iowait: Long = 0
        var irq: Long = 0
        var softirq: Long = 0
        var steal: Long = 0
        
        try {
            val reader = BufferedReader(FileReader("/proc/stat"))
            val cpuLine = reader.readLine()
            reader.close()
            
            val cpuInfos = cpuLine.split("\\s+".toRegex()).dropWhile { it.isEmpty() }
            user = cpuInfos[1].toLong()
            nice = cpuInfos[2].toLong()
            system = cpuInfos[3].toLong()
            idle = cpuInfos[4].toLong()
            iowait = cpuInfos[5].toLong()
            irq = cpuInfos[6].toLong()
            softirq = cpuInfos[7].toLong()
            steal = cpuInfos[8].toLong()
            
            val totalCpu = user + nice + system + idle + iowait + irq + softirq + steal
            val idleCpu = idle + iowait
            return (totalCpu - idleCpu) * 100f / totalCpu
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0f
    }
    
    private fun readAppCpuUsage(): Float {
        try {
            val pid = Process.myPid()
            val reader = BufferedReader(FileReader("/proc/$pid/stat"))
            val line = reader.readLine()
            reader.close()
            
            val values = line.split("\\s+".toRegex()).dropWhile { it.isEmpty() }
            val utime = values[13].toLong()
            val stime = values[14].toLong()
            val cutime = values[15].toLong()
            val cstime = values[16].toLong()
            
            val totalTime = utime + stime + cutime + cstime
            return totalTime * 100f / Runtime.getRuntime().availableProcessors()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return 0f
    }
}

/**
 * CPU 信息数据类
 */
data class CpuInfo(
    val appCpuUsage: Float,     // 应用 CPU 使用率
    val systemCpuUsage: Float,  // 系统 CPU 使用率
    val cpuFrequency: Long,     // CPU 频率（Hz）
    val cpuTemperature: Float,  // CPU 温度（℃）
    val cpuCoreCount: Int       // CPU 核心数
)