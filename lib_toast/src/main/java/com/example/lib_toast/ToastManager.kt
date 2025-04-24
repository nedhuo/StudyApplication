package com.example.lib_toast

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import com.example.lib_toast.databinding.LayoutToastBinding
import java.util.LinkedList
import java.util.Queue

/**
 * Toast 管理器
 */
object ToastManager {
    private val mainHandler = Handler(Looper.getMainLooper())
    private var currentToast: Toast? = null
    private val toastQueue: Queue<ToastInfo> = LinkedList()
    private var defaultConfig = ToastConfig()
    
    /**
     * 设置默认配置
     */
    fun setDefaultConfig(config: ToastConfig) {
        defaultConfig = config
    }
    
    /**
     * 显示 Toast
     */
    fun show(
        context: Context,
        message: String,
        config: ToastConfig = defaultConfig
    ) {
        val info = ToastInfo(context, message, config)
        
        if (!config.isAllowQueue) {
            cancelCurrent()
            showToast(info)
            return
        }
        
        if (currentToast == null) {
            showToast(info)
        } else if (toastQueue.size < config.maxQueueSize) {
            toastQueue.offer(info)
        }
    }
    
    /**
     * 显示成功 Toast
     */
    fun showSuccess(
        context: Context,
        message: String,
        config: ToastConfig = ToastConfig.SUCCESS
    ) = show(context, message, config)
    
    /**
     * 显示错误 Toast
     */
    fun showError(
        context: Context,
        message: String,
        config: ToastConfig = ToastConfig.ERROR
    ) = show(context, message, config)
    
    /**
     * 显示警告 Toast
     */
    fun showWarning(
        context: Context,
        message: String,
        config: ToastConfig = ToastConfig.WARNING
    ) = show(context, message, config)
    
    /**
     * 显示信息 Toast
     */
    fun showInfo(
        context: Context,
        message: String,
        config: ToastConfig = ToastConfig.INFO
    ) = show(context, message, config)
    
    /**
     * 取消当前 Toast
     */
    fun cancelCurrent() {
        currentToast?.cancel()
        currentToast = null
    }
    
    /**
     * 清空队列
     */
    fun clearQueue() {
        toastQueue.clear()
    }
    
    private fun showToast(info: ToastInfo) {
        mainHandler.post {
            try {
                showToastInternal(info)
            } catch (e: Exception) {
                // 发生异常时使用系统 Toast
                Toast.makeText(info.context, info.message, Toast.LENGTH_SHORT).show()
            }
        }
    }
    
    private fun showToastInternal(info: ToastInfo) {
        val binding = LayoutToastBinding.inflate(LayoutInflater.from(info.context))
        val config = info.config
        
        // 设置容器样式
        binding.toastContainer.apply {
            if (config.maxWidth > 0) {
                maxWidth = config.maxWidth
            }
            minimumHeight = config.minHeight
            setPadding(
                config.horizontalPadding,
                config.verticalPadding,
                config.horizontalPadding,
                config.verticalPadding
            )
            
            // 设置背景
            background = if (config.backgroundDrawable != 0) {
                ResourcesCompat.getDrawable(resources, config.backgroundDrawable, null)
            } else {
                GradientDrawable().apply {
                    cornerRadius = config.cornerRadius
                    setColor(config.backgroundColor)
                }
            }
        }
        
        // 设置图标
        binding.toastIcon.apply {
            if (config.iconRes != 0) {
                setImageResource(config.iconRes)
                layoutParams.width = config.iconSize
                layoutParams.height = config.iconSize
                (layoutParams as? android.widget.LinearLayout.LayoutParams)?.marginEnd = config.iconMarginEnd
                visibility = View.VISIBLE
            } else {
                visibility = View.GONE
            }
        }
        
        // 设置文本
        binding.toastMessage.apply {
            text = info.message
            setTextColor(config.textColor)
            textSize = config.textSize / resources.displayMetrics.scaledDensity
            maxLines = config.textMaxLines
            
            if (config.fontFamily != 0) {
                typeface = ResourcesCompat.getFont(context, config.fontFamily)
            }
            setTypeface(typeface, config.textStyle)
        }
        
        // 创建并显示 Toast
        Toast(info.context).apply {
            setGravity(config.gravity, config.xOffset, config.yOffset)
            duration = if (config.duration <= 2000) Toast.LENGTH_SHORT else Toast.LENGTH_LONG
            view = binding.root
            
            currentToast = this
            
            // 设置动画
            if (config.enterAnimation != 0 || config.exitAnimation != 0) {
                binding.root.animation = android.view.animation.AnimationUtils.loadAnimation(
                    info.context,
                    config.enterAnimation
                )
            }
            
            show()
            
            // 处理队列
            mainHandler.postDelayed({
                currentToast = null
                toastQueue.poll()?.let { nextInfo ->
                    showToast(nextInfo)
                }
            }, config.duration.toLong())
        }
    }
    
    private data class ToastInfo(
        val context: Context,
        val message: String,
        val config: ToastConfig
    )
} 