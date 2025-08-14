package com.example.lib_base.ui

import android.content.Context
import android.view.Gravity
import android.view.Window
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.LibBase.R
import com.example.LibBase.databinding.BaseDialogLoadingBinding
import com.example.lib_base.base.BaseDialog
import com.example.lib_base.ext.bindings
import com.nedhuo.libutils.utilcode.util.ActivityUtils
import com.nedhuo.libutils.utilcode.util.ConvertUtils
import com.nedhuo.libutils.utilcode.util.LogUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingDialog(context: Context = ActivityUtils.getTopActivity()) : BaseDialog(context, R.style.LoadingDialogStyle) {
    private val binding by bindings<BaseDialogLoadingBinding>()
    private val delayTime: Long = 5L
    private var countJob: Job? = null

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

    fun showLoading(content: String?) {
        showLoading(false, 5, content)
    }

    fun showLoading(autoClose: Boolean, duration: Long = 5L) {
        showLoading(autoClose, duration, null)
    }

    fun showLoading(autoClose: Boolean = false, duration: Long = 5L, content: String? = "加载中...") {
        if (autoClose) startDelayJob(duration)
        binding.tvMessageText.text = content.orEmpty()
        show()
    }

    fun dismissLoading() {
        dismiss()
    }

    /**
     * 加载弹框自动关闭
     */
    private fun startDelayJob(duration: Long = delayTime) {
        countJob = (context as? FragmentActivity)?.lifecycleScope?.launch {
            delay(duration * 1000)
            dismissLoading()
        }
    }

    private fun cancelDelayJob() {
        countJob?.cancel()
    }

    override fun dismiss() {
        cancelDelayJob()
        runCatching {
            super.dismiss()
        }.onFailure {
            LogUtils.e(TAG, it.message)
        }
    }
}