package com.example.lib_common.utils

import android.content.Context
import android.widget.Toast
import com.example.lib_common.manager.CommonManager

/**
 * 常用扩展函数
 */

fun Context.toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun toast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    CommonManager.getContext().toast(message, duration)
}

fun dp2px(dp: Float): Int {
    val scale = CommonManager.getContext().resources.displayMetrics.density
    return (dp * scale + 0.5f).toInt()
}

fun px2dp(px: Float): Int {
    val scale = CommonManager.getContext().resources.displayMetrics.density
    return (px / scale + 0.5f).toInt()
}

fun sp2px(sp: Float): Int {
    val scale = CommonManager.getContext().resources.displayMetrics.scaledDensity
    return (sp * scale + 0.5f).toInt()
}

fun px2sp(px: Float): Int {
    val scale = CommonManager.getContext().resources.displayMetrics.scaledDensity
    return (px / scale + 0.5f).toInt()
}