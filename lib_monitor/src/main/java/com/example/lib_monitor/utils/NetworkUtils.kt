package com.example.lib_monitor.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.net.TrafficStats
import android.os.Build
import android.telephony.TelephonyManager

/**
 * 网络监控工具类
 */
object NetworkUtils {
    
    /**
     * 获取网络信息
     */
    fun getNetworkInfo(context: Context): NetworkStatus {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkCapabilities = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager.activeNetwork?.let {
                connectivityManager.getNetworkCapabilities(it)
            }
        } else {
            null
        }
        
        val networkType = getNetworkType(context)
        val isConnected = isNetworkConnected(context)
        val mobileSignalLevel = getMobileSignalLevel(context)
        
        val rxBytes = TrafficStats.getTotalRxBytes()
        val txBytes = TrafficStats.getTotalTxBytes()
        val mobileRxBytes = TrafficStats.getMobileRxBytes()
        val mobileTxBytes = TrafficStats.getMobileTxBytes()
        
        val networkSpeed = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            networkCapabilities?.linkDownstreamBandwidthKbps ?: -1
        } else {
            -1
        }
        
        return NetworkStatus(
            isConnected = isConnected,
            networkType = networkType,
            mobileSignalLevel = mobileSignalLevel,
            totalRxBytes = rxBytes,
            totalTxBytes = txBytes,
            mobileRxBytes = mobileRxBytes,
            mobileTxBytes = mobileTxBytes,
            networkSpeed = networkSpeed,
            isWifi = isWifiNetwork(networkCapabilities),
            isCellular = isCellularNetwork(networkCapabilities),
            isVPN = isVPNNetwork(networkCapabilities),
            isRoaming = isRoaming(context)
        )
    }
    
    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
        } else {
            @Suppress("DEPRECATION")
            val networkInfo = connectivityManager.activeNetworkInfo
            return networkInfo?.isConnected == true
        }
    }
    
    private fun getNetworkType(context: Context): NetworkType {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val network = connectivityManager.activeNetwork
            val capabilities = connectivityManager.getNetworkCapabilities(network)
            return when {
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true -> NetworkType.WIFI
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true -> {
                    getMobileNetworkType(context)
                }
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true -> NetworkType.VPN
                capabilities?.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) == true -> NetworkType.ETHERNET
                else -> NetworkType.UNKNOWN
            }
        } else {
            @Suppress("DEPRECATION")
            return when (connectivityManager.activeNetworkInfo?.type) {
                android.net.ConnectivityManager.TYPE_WIFI -> NetworkType.WIFI
                android.net.ConnectivityManager.TYPE_MOBILE -> getMobileNetworkType(context)
                android.net.ConnectivityManager.TYPE_VPN -> NetworkType.VPN
                android.net.ConnectivityManager.TYPE_ETHERNET -> NetworkType.ETHERNET
                else -> NetworkType.UNKNOWN
            }
        }
    }
    
    private fun getMobileNetworkType(context: Context): NetworkType {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return when (telephonyManager.networkType) {
            TelephonyManager.NETWORK_TYPE_GPRS,
            TelephonyManager.NETWORK_TYPE_EDGE,
            TelephonyManager.NETWORK_TYPE_CDMA,
            TelephonyManager.NETWORK_TYPE_1xRTT,
            TelephonyManager.NETWORK_TYPE_IDEN -> NetworkType.MOBILE_2G
            
            TelephonyManager.NETWORK_TYPE_UMTS,
            TelephonyManager.NETWORK_TYPE_EVDO_0,
            TelephonyManager.NETWORK_TYPE_EVDO_A,
            TelephonyManager.NETWORK_TYPE_HSDPA,
            TelephonyManager.NETWORK_TYPE_HSUPA,
            TelephonyManager.NETWORK_TYPE_HSPA,
            TelephonyManager.NETWORK_TYPE_EVDO_B,
            TelephonyManager.NETWORK_TYPE_EHRPD,
            TelephonyManager.NETWORK_TYPE_HSPAP -> NetworkType.MOBILE_3G
            
            TelephonyManager.NETWORK_TYPE_LTE -> NetworkType.MOBILE_4G
            
            TelephonyManager.NETWORK_TYPE_NR -> NetworkType.MOBILE_5G
            
            else -> NetworkType.MOBILE_UNKNOWN
        }
    }
    
    private fun getMobileSignalLevel(context: Context): Int {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            telephonyManager.signalStrength?.level ?: -1
        } else {
            -1
        }
    }
    
    private fun isWifiNetwork(networkCapabilities: NetworkCapabilities?): Boolean {
        return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) == true
    }
    
    private fun isCellularNetwork(networkCapabilities: NetworkCapabilities?): Boolean {
        return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) == true
    }
    
    private fun isVPNNetwork(networkCapabilities: NetworkCapabilities?): Boolean {
        return networkCapabilities?.hasTransport(NetworkCapabilities.TRANSPORT_VPN) == true
    }
    
    private fun isRoaming(context: Context): Boolean {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        return telephonyManager.isNetworkRoaming
    }
}

/**
 * 网络信息数据类
 */
data class NetworkStatus(
    val isConnected: Boolean,        // 是否已连接网络
    val networkType: NetworkType,    // 网络类型
    val mobileSignalLevel: Int,      // 移动信号强度（0-4，-1表示未知）
    val totalRxBytes: Long,          // 总接收字节数
    val totalTxBytes: Long,          // 总发送字节数
    val mobileRxBytes: Long,         // 移动网络接收字节数
    val mobileTxBytes: Long,         // 移动网络发送字节数
    val networkSpeed: Int,           // 网络速度（Kbps，-1表示未知）
    val isWifi: Boolean,            // 是否是 WiFi 网络
    val isCellular: Boolean,        // 是否是蜂窝网络
    val isVPN: Boolean,             // 是否是 VPN 网络
    val isRoaming: Boolean          // 是否正在漫游
)

/**
 * 网络类型枚举
 */
enum class NetworkType {
    WIFI,           // WiFi
    MOBILE_2G,      // 2G 网络
    MOBILE_3G,      // 3G 网络
    MOBILE_4G,      // 4G 网络
    MOBILE_5G,      // 5G 网络
    MOBILE_UNKNOWN, // 未知移动网络
    VPN,            // VPN
    ETHERNET,       // 以太网
    UNKNOWN         // 未知网络
} 