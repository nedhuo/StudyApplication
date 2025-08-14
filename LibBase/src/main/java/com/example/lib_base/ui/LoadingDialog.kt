package com.example.lib_base.ui

import android.content.Context
import android.view.Gravity
import android.view.Window
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoadingDialog(context: Context = ActivityUtils.getTopActivity()) : BaseDialog(context, R.style.LoadingDialogStyle), DefaultLifecycleObserver {
    private val binding by bindings<BaseDialogLoadingBinding>()

    private var countJob: Job? = null

    companion object {
        private val TAG = LoadingDialog::class.java.simpleName
    }

    init {
        (context as? LifecycleOwner)?.lifecycle?.addObserver(this)
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

    fun showLoading(content: String = "加载中...") {
        showLoading(LoadingConfig(content))
    }

    fun showLoading(config: LoadingConfig) {
        if (isShowing) {
            binding.tvMessageText.text = config.content
            if (config.autoClose) {
                startDelayJob(config.duration)
            } else {
                cancelDelayJob()
            }
            return
        }
        if (config.autoClose) startDelayJob(config.duration)
        binding.tvMessageText.text = config.content
        show()
    }

    fun dismissLoading() {
        dismiss()
    }

    /**
     * 加载弹框自动关闭
     */
    @OptIn(DelicateCoroutinesApi::class)
    private fun startDelayJob(duration: Long) {
        cancelDelayJob()
        countJob = (context as? LifecycleOwner)?.lifecycleScope?.launch {
            delay(duration * 1000)
            dismissLoading()
        } ?: GlobalScope.launch(Dispatchers.Main) {
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

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        dismissLoading()
        (context as? LifecycleOwner)?.lifecycle?.removeObserver(this)
    }
}


data class LoadingConfig(
    val content: String = "加载中...",
    val autoClose: Boolean = false,
    val duration: Long = 5L,
    val cancelable: Boolean = false,
)
