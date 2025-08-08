package com.example.lib_utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

/**
 * Toast 工具类
 */
object ToastUtils {
    private var toast: Toast? = null
    
    /**
     * 显示短时间 Toast
     */
    fun showShort(context: Context?, message: String?) {
        show(context, message, Toast.LENGTH_SHORT)
    }
    
    /**
     * 显示短时间 Toast
     */
    fun showShort(context: Context?, @StringRes messageResId: Int) {
        show(context, context?.getString(messageResId), Toast.LENGTH_SHORT)
    }
    
    /**
     * 显示长时间 Toast
     */
    fun showLong(context: Context?, message: String?) {
        show(context, message, Toast.LENGTH_LONG)
    }
    
    /**
     * 显示长时间 Toast
     */
    fun showLong(context: Context?, @StringRes messageResId: Int) {
        show(context, context?.getString(messageResId), Toast.LENGTH_LONG)
    }
    
    /**
     * 显示 Toast
     */
    private fun show(context: Context?, message: String?, duration: Int) {
        if (context == null || message.isNullOrEmpty()) return
        
        cancelToast()
        toast = Toast.makeText(context.applicationContext, message, duration).apply {
            show()
        }
    }
    
    /**
     * 取消 Toast
     */
    fun cancelToast() {
        toast?.cancel()
        toast = null
    }
}