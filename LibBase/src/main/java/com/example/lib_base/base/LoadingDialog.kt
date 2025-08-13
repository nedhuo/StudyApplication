package com.example.lib_base.base

import android.content.Context
import android.view.Gravity
import android.view.Window
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.LibBase.R
import com.example.LibBase.databinding.BaseDialogLoadingBinding
import com.example.lib_base.ext.bindings
import com.nedhuo.libutils.utilcode.util.ActivityUtils
import com.nedhuo.libutils.utilcode.util.ConvertUtils
import com.nedhuo.libutils.utilcode.util.LogUtils
import java.util.Timer
import java.util.TimerTask


class LoadingDialog(context: Context = ActivityUtils.getTopActivity()) : BaseDialog(context, R.style.LoadingDialogStyle) {
    private val binding by bindings<BaseDialogLoadingBinding>()
    private val delayTime: Long = 5L
    private var countTimer: Timer? = null

    companion object {
        private val TAG = LoadingDialog::class.java.simpleName
    }

    override fun initView() {
        Glide.with(context).load(R.mipmap.base_ic_loading)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(binding.ivLoading)
    }

    override fun initData() {
    }

    override fun initStyle(window: Window?) {
        this.setCancelable(false)
        this.setCanceledOnTouchOutside(false)
        window?.setLayout(ConvertUtils.dp2px(65f), ConvertUtils.dp2px(65f))
        window?.setGravity(Gravity.CENTER)
    }

    fun showLoading(content: String? = null) {
        showLoading(content = content)
    }

    fun showLoading(autoClose: Boolean, duration: Long = 5L) {
        showLoading(autoClose, duration)
    }

    fun showLoading(autoClose: Boolean = false, duration: Long = 5L, content: String? = "加载中...") {
        if (autoClose) startCountdown(duration)
        binding.tvMessageText.text = content.orEmpty()
        show()
    }

    fun dismissLoading() {
        dismiss()
    }

    /**
     * 加载弹框自动关闭
     */
    private fun startCountdown(duration: Long = delayTime) {
        countTimer = Timer()
        countTimer?.schedule(object : TimerTask() {
            override fun run() {
                dismissLoading()
            }
        }, duration * 1000)
    }

    private fun cancelTimer() {
        countTimer?.cancel()
    }

    override fun dismiss() {
        cancelTimer()
        runCatching {
            super.dismiss()
        }.onFailure {
            LogUtils.e(TAG, it.message)
        }
    }
}