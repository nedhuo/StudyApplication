package com.example.lib_monitor.utils

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.os.Build

/**
 * 电池监控工具类
 */
object BatteryUtils {
    
    /**
     * 获取电池信息
     */
    fun getBatteryInfo(context: Context): BatteryInfo {
        val batteryStatus = context.registerReceiver(null,
            IntentFilter(Intent.ACTION_BATTERY_CHANGED))
            
        return BatteryInfo(
            level = getBatteryLevel(batteryStatus),
            scale = getBatteryScale(batteryStatus),
            percentage = getBatteryPercentage(batteryStatus),
            status = getBatteryStatus(batteryStatus),
            health = getBatteryHealth(batteryStatus),
            temperature = getBatteryTemperature(batteryStatus),
            voltage = getBatteryVoltage(batteryStatus),
            technology = getBatteryTechnology(batteryStatus),
            isCharging = isCharging(batteryStatus),
            chargingSource = getChargingSource(batteryStatus),
            remainingEnergy = getRemainingEnergy(batteryStatus),
            batteryCapacity = getBatteryCapacity(context)
        )
    }
    
    private fun getBatteryLevel(intent: Intent?): Int {
        return intent?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
    }
    
    private fun getBatteryScale(intent: Intent?): Int {
        return intent?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
    }
    
    private fun getBatteryPercentage(intent: Intent?): Float {
        val level = getBatteryLevel(intent)
        val scale = getBatteryScale(intent)
        return if (level != -1 && scale != -1) {
            level * 100f / scale
        } else {
            -1f
        }
    }
    
    private fun getBatteryStatus(intent: Intent?): BatteryStatus {
        return when (intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1)) {
            BatteryManager.BATTERY_STATUS_CHARGING -> BatteryStatus.CHARGING
            BatteryManager.BATTERY_STATUS_DISCHARGING -> BatteryStatus.DISCHARGING
            BatteryManager.BATTERY_STATUS_FULL -> BatteryStatus.FULL
            BatteryManager.BATTERY_STATUS_NOT_CHARGING -> BatteryStatus.NOT_CHARGING
            else -> BatteryStatus.UNKNOWN
        }
    }
    
    private fun getBatteryHealth(intent: Intent?): BatteryHealth {
        return when (intent?.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)) {
            BatteryManager.BATTERY_HEALTH_COLD -> BatteryHealth.COLD
            BatteryManager.BATTERY_HEALTH_DEAD -> BatteryHealth.DEAD
            BatteryManager.BATTERY_HEALTH_GOOD -> BatteryHealth.GOOD
            BatteryManager.BATTERY_HEALTH_OVERHEAT -> BatteryHealth.OVERHEAT
            BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> BatteryHealth.OVER_VOLTAGE
            BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> BatteryHealth.UNSPECIFIED_FAILURE
            else -> BatteryHealth.UNKNOWN
        }
    }
    
    private fun getBatteryTemperature(intent: Intent?): Float {
        return intent?.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, -1)?.div(10f) ?: -1f
    }
    
    private fun getBatteryVoltage(intent: Intent?): Int {
        return intent?.getIntExtra(BatteryManager.EXTRA_VOLTAGE, -1) ?: -1
    }
    
    private fun getBatteryTechnology(intent: Intent?): String {
        return intent?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY) ?: "Unknown"
    }
    
    private fun isCharging(intent: Intent?): Boolean {
        val status = intent?.getIntExtra(BatteryManager.EXTRA_STATUS, -1) ?: -1
        return status == BatteryManager.BATTERY_STATUS_CHARGING ||
                status == BatteryManager.BATTERY_STATUS_FULL
    }
    
    private fun getChargingSource(intent: Intent?): ChargingSource {
        return when (intent?.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)) {
            BatteryManager.BATTERY_PLUGGED_AC -> ChargingSource.AC
            BatteryManager.BATTERY_PLUGGED_USB -> ChargingSource.USB
            BatteryManager.BATTERY_PLUGGED_WIRELESS -> ChargingSource.WIRELESS
            else -> ChargingSource.NONE
        }
    }
    
    private fun getRemainingEnergy(intent: Intent?): Float {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val batteryManager = BatteryManager::class.java.newInstance()
            return batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_ENERGY_COUNTER).toFloat()
        }
        return -1f
    }
    
    private fun getBatteryCapacity(context: Context): Double {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager
            val chargeCounter = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER)
            val capacity = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
            return if (chargeCounter != Int.MIN_VALUE && capacity != Int.MIN_VALUE) {
                chargeCounter / capacity.toDouble()
            } else {
                -1.0
            }
        }
        return -1.0
    }
}

/**
 * 电池信息数据类
 */
data class BatteryInfo(
    val level: Int,                 // 当前电量
    val scale: Int,                 // 最大电量
    val percentage: Float,          // 电量百分比
    val status: BatteryStatus,      // 电池状态
    val health: BatteryHealth,      // 电池健康状况
    val temperature: Float,         // 电池温度（摄氏度）
    val voltage: Int,               // 电池电压（mV）
    val technology: String,         // 电池技术
    val isCharging: Boolean,        // 是否正在充电
    val chargingSource: ChargingSource, // 充电源
    val remainingEnergy: Float,     // 剩余能量（nWh）
    val batteryCapacity: Double     // 电池容量（mAh）
)

/**
 * 电池状态枚举
 */
enum class BatteryStatus {
    CHARGING,       // 充电中
    DISCHARGING,    // 放电中
    FULL,           // 已充满
    NOT_CHARGING,   // 未充电
    UNKNOWN         // 未知状态
}

/**
 * 电池健康状况枚举
 */
enum class BatteryHealth {
    COLD,                   // 温度过低
    DEAD,                   // 电池损坏
    GOOD,                   // 状况良好
    OVERHEAT,              // 温度过高
    OVER_VOLTAGE,          // 电压过高
    UNSPECIFIED_FAILURE,   // 未指明的故障
    UNKNOWN                 // 未知状态
}

/**
 * 充电源枚举
 */
enum class ChargingSource {
    AC,         // 交流电源
    USB,        // USB
    WIRELESS,   // 无线充电
    NONE        // 未充电
} 