package com.example.lib_base.base

import android.view.Gravity
import android.view.Window
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.OnLifecycleEvent
import com.example.LibBase.R
import com.nedhuo.libutils.utilcode.util.ConvertUtils
import java.util.*

/**
 *Author:shanqiang
 *Date:2022/3/12 6:41 下午
 *Description: 全局加载弹窗loading
 */
class LoadingDialog : BaseDialog(
    ActivityUtils.getTopActivity(),
    R.style.LoadingDialogStyle
) {

    val duration: Long = 5L
    var startTime: Long = 0
    var countTimer: Timer? = null
    private var isDes = false
    private val transformation: WebpDrawableTransformation by lazy {
        val centerInside = CenterInside()
        WebpDrawableTransformation(centerInside)
    }

    override fun getLayoutId(): Int {
        return R.layout.dialog_loading
    }

    override fun initView() {
        Glide.with(context).load(R.mipmap.common_ic_loading)
            .optionalTransform(WebpDrawable::class.java, transformation)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(mBinding.ivLoading)
    }

    override fun initData() {
    }

    override fun initStyle(window: Window?) {
        this.setCancelable(false)
        this.setCanceledOnTouchOutside(false)
        window?.setLayout(ConvertUtils.dp2px(65f), ConvertUtils.dp2px(65f))
        window?.setGravity(Gravity.CENTER)
    }

    //显示弹窗
    fun showDialog() {
        if (!isShowing) {
            show()
        }
    }

    /**
     * 5s中加载弹框自动关闭
     */
    private fun startCountdown() {
        startTime = System.currentTimeMillis()
        countTimer = Timer()
        countTimer?.schedule(object : TimerTask() {
            override fun run() {
                disDialog()
            }
        }, duration * 1000)
    }

    private fun cancelTimer() {
        countTimer?.cancel()
    }

    //关闭弹窗
    fun disDialog() {
        if (isShowing) {
            dismiss()
        }
    }

    override fun dismiss() {
        cancelTimer()
        runCatching {
            super.dismiss()
        }.onFailure {
            LogUtils.e("dialog", it.message)
        }.onSuccess {
            LogUtils.i("dialog", "success")
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun destroy() {
        cancelTimer()
        isDes = true
    }

}