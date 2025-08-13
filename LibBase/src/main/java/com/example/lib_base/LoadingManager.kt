package com.example.lib_base

import android.app.Activity
import com.example.lib_base.ui.LoadingDialog
import com.nedhuo.libutils.utilcode.util.ActivityUtils

/**
 * author : nedhuo
 * date : 2025/8/13 21:01
 * desc : 除了Activity，Fragment等页面的Loading外，维护一个全局的Loading，在非Activity页面调用
 */
object LoadingManager {

    private var loadingDialog: LoadingDialog? = null

    fun showLoading(content: String = "加载中...", context: Activity = ActivityUtils.getTopActivity()) {
        if (loadingDialog == null) loadingDialog = LoadingDialog(context)
        loadingDialog?.showLoading(content)
    }

    fun dismissLoading() {
        loadingDialog?.dismissLoading()
    }
}