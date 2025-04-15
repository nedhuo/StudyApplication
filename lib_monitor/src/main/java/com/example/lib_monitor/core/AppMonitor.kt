package com.example.lib_monitor.core

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.lib_log.LogUtils
import com.example.lib_monitor.utils.BatteryUtils
import com.example.lib_monitor.utils.CpuUtils
import com.example.lib_monitor.utils.MemoryUtils
import com.example.lib_monitor.utils.NetworkUtils
import com.example.lib_monitor.utils.NetworkStatus
import com.example.lib_monitor.utils.BlockCanary
import com.example.lib_monitor.utils.FpsMonitor
import com.example.lib_monitor.utils.AnrMonitor

/**
 * APP 性能监控管理类
 */
object AppMonitor {
    private const val TAG = "AppMonitor"
    
    // 应用启动时间
    private var appStartTime = 0L
    
    // 页面加载时间记录
    private val pageLoadTimes = mutableMapOf<String, Long>()
    
    // 是否是冷启动
    private var isColdStart = true
    
    // CPU 监控间隔（毫秒）
    private const val CPU_MONITOR_INTERVAL = 5000L
    
    // 电池监控间隔（毫秒）
    private const val BATTERY_MONITOR_INTERVAL = 60000L
    
    // 网络监控间隔（毫秒）
    private const val NETWORK_MONITOR_INTERVAL = 10000L
    
    // 内存监控间隔（毫秒）
    private const val MEMORY_MONITOR_INTERVAL = 5000L
    
    // CPU 监控 Handler
    private val cpuMonitorHandler = Handler(Looper.getMainLooper())
    
    // 电池监控 Handler
    private val batteryMonitorHandler = Handler(Looper.getMainLooper())
    
    // 网络监控 Handler
    private val networkMonitorHandler = Handler(Looper.getMainLooper())
    
    // 内存监控 Handler
    private val memoryMonitorHandler = Handler(Looper.getMainLooper())
    
    // CPU 监控任务
    private val cpuMonitorRunnable = object : Runnable {
        override fun run() {
            monitorCpu()
            cpuMonitorHandler.postDelayed(this, CPU_MONITOR_INTERVAL)
        }
    }
    
    // 电池监控任务
    private val batteryMonitorRunnable = object : Runnable {
        override fun run() {
            monitorBattery()
            batteryMonitorHandler.postDelayed(this, BATTERY_MONITOR_INTERVAL)
        }
    }
    
    // 网络监控任务
    private val networkMonitorRunnable = object : Runnable {
        override fun run() {
            monitorNetwork()
            networkMonitorHandler.postDelayed(this, NETWORK_MONITOR_INTERVAL)
        }
    }
    
    // 内存监控任务
    private val memoryMonitorRunnable = object : Runnable {
        override fun run() {
            monitorMemory()
            memoryMonitorHandler.postDelayed(this, MEMORY_MONITOR_INTERVAL)
        }
    }
    
    // 应用上下文
    private lateinit var appContext: Context
    
    // 上次网络流量统计
    private var lastTotalRxBytes = 0L
    private var lastTotalTxBytes = 0L
    private var lastMobileRxBytes = 0L
    private var lastMobileTxBytes = 0L
    private var lastNetworkStatsTime = 0L
    
    /**
     * 初始化监控
     */
    fun init(application: Application) {
        appContext = application.applicationContext
        appStartTime = SystemClock.elapsedRealtime()
        lastNetworkStatsTime = SystemClock.elapsedRealtime()
        
        // 初始化 ANR 监控
        AnrMonitor.init(application)
        
        // 注册应用生命周期监听
        ProcessLifecycleOwner.get().lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                when (event) {
                    Lifecycle.Event.ON_START -> {
                        if (isColdStart) {
                            val startupTime = SystemClock.elapsedRealtime() - appStartTime
                            LogUtils.d(TAG, "App cold start time: $startupTime ms")
                            isColdStart = false
                            
                            // 启动所有监控
                            startCpuMonitor()
                            startBatteryMonitor()
                            startNetworkMonitor()
                            startMemoryMonitor()
                            BlockCanary.start()
                            FpsMonitor.start()
                            AnrMonitor.start()  // 启动 ANR 监控
                        } else {
                            val warmStartTime = SystemClock.elapsedRealtime() - appStartTime
                            LogUtils.d(TAG, "App warm start time: $warmStartTime ms")
                        }
                    }
                    Lifecycle.Event.ON_STOP -> {
                        appStartTime = SystemClock.elapsedRealtime()
                        // 停止所有监控
                        stopCpuMonitor()
                        stopBatteryMonitor()
                        stopNetworkMonitor()
                        stopMemoryMonitor()
                        BlockCanary.stop()
                        FpsMonitor.stop()
                        AnrMonitor.stop()  // 停止 ANR 监控
                    }
                    else -> {}
                }
            }
        })
        
        // 注册 Activity 生命周期监听
        application.registerActivityLifecycleCallbacks(object : Application.ActivityLifecycleCallbacks {
            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                pageLoadTimes[activity.javaClass.simpleName] = SystemClock.elapsedRealtime()
            }
            
            override fun onActivityStarted(activity: Activity) {}
            
            override fun onActivityResumed(activity: Activity) {
                val loadTime = SystemClock.elapsedRealtime() - (pageLoadTimes[activity.javaClass.simpleName] ?: 0L)
                LogUtils.d(TAG, "Page ${activity.javaClass.simpleName} load time: $loadTime ms")
            }
            
            override fun onActivityPaused(activity: Activity) {}
            
            override fun onActivityStopped(activity: Activity) {}
            
            override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
            
            override fun onActivityDestroyed(activity: Activity) {
                pageLoadTimes.remove(activity.javaClass.simpleName)
            }
        })
    }
    
    /**
     * 启动 CPU 监控
     */
    private fun startCpuMonitor() {
        cpuMonitorHandler.post(cpuMonitorRunnable)
    }
    
    /**
     * 停止 CPU 监控
     */
    private fun stopCpuMonitor() {
        cpuMonitorHandler.removeCallbacks(cpuMonitorRunnable)
    }
    
    /**
     * 启动电池监控
     */
    private fun startBatteryMonitor() {
        batteryMonitorHandler.post(batteryMonitorRunnable)
    }
    
    /**
     * 停止电池监控
     */
    private fun stopBatteryMonitor() {
        batteryMonitorHandler.removeCallbacks(batteryMonitorRunnable)
    }
    
    /**
     * 启动网络监控
     */
    private fun startNetworkMonitor() {
        networkMonitorHandler.post(networkMonitorRunnable)
    }
    
    /**
     * 停止网络监控
     */
    private fun stopNetworkMonitor() {
        networkMonitorHandler.removeCallbacks(networkMonitorRunnable)
    }
    
    /**
     * 启动内存监控
     */
    private fun startMemoryMonitor() {
        memoryMonitorHandler.post(memoryMonitorRunnable)
    }
    
    /**
     * 停止内存监控
     */
    private fun stopMemoryMonitor() {
        memoryMonitorHandler.removeCallbacks(memoryMonitorRunnable)
    }
    
    /**
     * 监控 CPU
     */
    private fun monitorCpu() {
        val cpuInfo = CpuUtils.getCpuInfo()
        LogUtils.i(TAG, """
            CPU Info:
            - App CPU Usage: ${String.format("%.1f", cpuInfo.appCpuUsage)}%
            - System CPU Usage: ${String.format("%.1f", cpuInfo.systemCpuUsage)}%
            - CPU Frequency: ${cpuInfo.cpuFrequency / 1000}MHz
            - CPU Temperature: ${String.format("%.1f", cpuInfo.cpuTemperature)}°C
            - CPU Core Count: ${cpuInfo.cpuCoreCount}
        """.trimIndent())
    }
    
    /**
     * 监控电池
     */
    private fun monitorBattery() {
        val batteryInfo = BatteryUtils.getBatteryInfo(appContext)
        LogUtils.i(TAG, """
            Battery Info:
            - Level: ${batteryInfo.level}/${batteryInfo.scale} (${String.format("%.1f", batteryInfo.percentage)}%)
            - Status: ${batteryInfo.status}
            - Health: ${batteryInfo.health}
            - Temperature: ${String.format("%.1f", batteryInfo.temperature)}°C
            - Voltage: ${batteryInfo.voltage}mV
            - Technology: ${batteryInfo.technology}
            - Charging: ${if (batteryInfo.isCharging) "Yes (${batteryInfo.chargingSource})" else "No"}
            - Capacity: ${String.format("%.2f", batteryInfo.batteryCapacity)}mAh
            - Remaining Energy: ${if (batteryInfo.remainingEnergy >= 0) "${batteryInfo.remainingEnergy}nWh" else "N/A"}
        """.trimIndent())
    }
    
    /**
     * 监控网络
     */
    private fun monitorNetwork() {
        val networkStatus = NetworkUtils.getNetworkInfo(appContext)
        val currentTime = SystemClock.elapsedRealtime()
        val timeInterval = (currentTime - lastNetworkStatsTime) / 1000f // 转换为秒
        
        // 计算网络速率
        val totalRxSpeed = if (lastTotalRxBytes > 0) {
            (networkStatus.totalRxBytes - lastTotalRxBytes) / timeInterval
        } else 0f
        
        val totalTxSpeed = if (lastTotalTxBytes > 0) {
            (networkStatus.totalTxBytes - lastTotalTxBytes) / timeInterval
        } else 0f
        
        val mobileRxSpeed = if (lastMobileRxBytes > 0) {
            (networkStatus.mobileRxBytes - lastMobileRxBytes) / timeInterval
        } else 0f
        
        val mobileTxSpeed = if (lastMobileTxBytes > 0) {
            (networkStatus.mobileTxBytes - lastMobileTxBytes) / timeInterval
        } else 0f
        
        // 更新上次统计数据
        lastTotalRxBytes = networkStatus.totalRxBytes
        lastTotalTxBytes = networkStatus.totalTxBytes
        lastMobileRxBytes = networkStatus.mobileRxBytes
        lastMobileTxBytes = networkStatus.mobileTxBytes
        lastNetworkStatsTime = currentTime
        
        LogUtils.i(TAG, """
            Network Info:
            - Connected: ${networkStatus.isConnected}
            - Type: ${networkStatus.networkType}
            - Signal Level: ${networkStatus.mobileSignalLevel}
            - Network Speed: ${networkStatus.networkSpeed}Kbps
            - WiFi: ${networkStatus.isWifi}
            - Mobile: ${networkStatus.isCellular}
            - VPN: ${networkStatus.isVPN}
            - Roaming: ${networkStatus.isRoaming}
            
            Traffic Stats:
            - Total Download: ${formatBytes(networkStatus.totalRxBytes)} (${formatSpeed(totalRxSpeed)})
            - Total Upload: ${formatBytes(networkStatus.totalTxBytes)} (${formatSpeed(totalTxSpeed)})
            - Mobile Download: ${formatBytes(networkStatus.mobileRxBytes)} (${formatSpeed(mobileRxSpeed)})
            - Mobile Upload: ${formatBytes(networkStatus.mobileTxBytes)} (${formatSpeed(mobileTxSpeed)})
        """.trimIndent())
    }
    
    /**
     * 监控内存
     */
    private fun monitorMemory() {
        val memoryInfo = MemoryUtils.getMemoryInfo(appContext)
        LogUtils.i(TAG, """
            Memory Info:
            App Memory:
            - Native Heap Size: ${formatBytes(memoryInfo.nativeHeapSize)}
            - Native Heap Allocated: ${formatBytes(memoryInfo.nativeHeapAllocated)}
            - Native Heap Free: ${formatBytes(memoryInfo.nativeHeapFree)}
            - Dalvik PSS: ${formatBytes(memoryInfo.dalvikPss * 1024)}
            - Native PSS: ${formatBytes(memoryInfo.nativePss * 1024)}
            - Total PSS: ${formatBytes(memoryInfo.totalPss * 1024)}
            
            System Memory:
            - Total Memory: ${formatBytes(memoryInfo.totalMemory)}
            - Available Memory: ${formatBytes(memoryInfo.availableMemory)}
            - Memory Usage: ${String.format("%.1f", memoryInfo.memoryUsagePercent)}%
            - Low Memory: ${memoryInfo.lowMemory}
            - Low Memory Threshold: ${formatBytes(memoryInfo.lowMemoryThreshold)}
        """.trimIndent())
    }
    
    /**
     * 格式化字节数
     */
    private fun formatBytes(bytes: Long): String {
        return when {
            bytes < 1024 -> "$bytes B"
            bytes < 1024 * 1024 -> String.format("%.1f KB", bytes / 1024f)
            bytes < 1024 * 1024 * 1024 -> String.format("%.1f MB", bytes / (1024f * 1024f))
            else -> String.format("%.1f GB", bytes / (1024f * 1024f * 1024f))
        }
    }
    
    /**
     * 格式化速率
     */
    private fun formatSpeed(bytesPerSecond: Float): String {
        return when {
            bytesPerSecond < 1024 -> String.format("%.1f B/s", bytesPerSecond)
            bytesPerSecond < 1024 * 1024 -> String.format("%.1f KB/s", bytesPerSecond / 1024)
            bytesPerSecond < 1024 * 1024 * 1024 -> String.format("%.1f MB/s", bytesPerSecond / (1024 * 1024))
            else -> String.format("%.1f GB/s", bytesPerSecond / (1024 * 1024 * 1024))
        }
    }
    
    /**
     * 记录自定义性能点
     */
    fun trackPerformancePoint(tag: String, point: String) {
        LogUtils.d(TAG, "Performance point - $tag: $point at ${SystemClock.elapsedRealtime()} ms")
    }
}