package com.example.lib_toast

import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.FontRes

/**
 * Toast 配置类
 */
data class ToastConfig(
    // 布局配置
    val maxWidth: Int = -1,                          // 最大宽度，-1 表示不限制
    val minHeight: Int = 48.dp,                      // 最小高度
    val horizontalPadding: Int = 24.dp,              // 水平内边距
    val verticalPadding: Int = 12.dp,               // 垂直内边距
    val cornerRadius: Float = 24f.dp,                // 圆角半径
    val gravity: Int = Gravity.BOTTOM,               // 显示位置
    val xOffset: Int = 0,                           // X 轴偏移
    val yOffset: Int = 64.dp,                       // Y 轴偏移
    
    // 背景配置
    @ColorInt val backgroundColor: Int = Color.parseColor("#CC000000"), // 背景颜色
    @DrawableRes val backgroundDrawable: Int = 0,    // 背景图片资源 ID
    
    // 文字配置
    @ColorInt val textColor: Int = Color.WHITE,      // 文字颜色
    val textSize: Float = 14f.sp,                    // 文字大小
    val textStyle: Int = Typeface.NORMAL,            // 文字样式
    @FontRes val fontFamily: Int = 0,                // 字体资源 ID
    val textMaxLines: Int = 2,                       // 最大行数
    
    // 图标配置
    @DrawableRes val iconRes: Int = 0,              // 图标资源 ID
    val iconSize: Int = 24.dp,                      // 图标大小
    val iconMarginEnd: Int = 8.dp,                  // 图标右边距
    
    // 动画配置
    val animationDuration: Long = 200,              // 动画时长
    val enterAnimation: Int = android.R.anim.fade_in,      // 进入动画
    val exitAnimation: Int = android.R.anim.fade_out,      // 退出动画
    
    // 行为配置
    val duration: Int = 2000,                        // 显示时长
    val isAllowQueue: Boolean = true,                // 是否允许排队
    val maxQueueSize: Int = 3                        // 最大排队数量
) {
    companion object {
        // 预定义样式
        val SUCCESS = ToastConfig(
            backgroundColor = Color.parseColor("#CC28a745"),
            textColor = Color.WHITE
        )
        
        val ERROR = ToastConfig(
            backgroundColor = Color.parseColor("#CCdc3545"),
            textColor = Color.WHITE
        )
        
        val WARNING = ToastConfig(
            backgroundColor = Color.parseColor("#CCffc107"),
            textColor = Color.BLACK
        )
        
        val INFO = ToastConfig(
            backgroundColor = Color.parseColor("#CC17a2b8"),
            textColor = Color.WHITE
        )
    }
} 