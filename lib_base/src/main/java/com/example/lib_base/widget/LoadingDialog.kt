package com.example.lib_base.widget

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.TextView
import androidx.core.view.isVisible
import com.example.lib_base.R
import com.google.android.material.progressindicator.CircularProgressIndicator

/**
 * 加载对话框
 */
class LoadingDialog(
    context: Context,
    private val message: String = "加载中..."
) : Dialog(context) {

    private lateinit var progressBar: CircularProgressIndicator
    private lateinit var messageText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.dialog_loading)
        
        // 设置背景透明
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        
        // 初始化视图
        progressBar = findViewById(R.id.progressBar)
        messageText = findViewById(R.id.messageText)
        
        // 设置消息
        messageText.text = message
        messageText.isVisible = message.isNotEmpty()
        
        // 设置不可取消
        setCancelable(false)
    }

    /**
     * 更新加载消息
     */
    fun updateMessage(message: String) {
        messageText.text = message
        messageText.isVisible = message.isNotEmpty()
    }
}