package com.example.lib_webview.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.LibWebView.R
import com.example.LibWebView.databinding.FragmentWebViewBinding
import com.example.lib_base.base.BaseFragment
import com.example.lib_base.ext.bindings
import com.example.lib_webview.view.BaseWebView

/**
 * 基础 WebView Fragment
 */
open class BaseWebViewFragment : BaseFragment() {
    private val binding by bindings<FragmentWebViewBinding>()

    protected var webView: BaseWebView? = null
    protected var url: String? = null
    protected var title: String? = null

    companion object {
        private const val ARG_URL = "arg_url"
        private const val ARG_TITLE = "arg_title"

        fun newInstance(url: String, title: String? = null): BaseWebViewFragment {
            return BaseWebViewFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_URL, url)
                    putString(ARG_TITLE, title)
                }
            }
        }
    }

    override fun initArguments(arguments: Bundle?) {
        url = arguments?.getString(ARG_URL)
        title = arguments?.getString(ARG_TITLE)
    }

    override fun initView() {
        initWebView()
    }

    override fun initData() {

        loadUrl()
    }

    protected open fun initWebView() {
        binding.webView.apply {
            // 初始化 WebView 设置
            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                useWideViewPort = true
                loadWithOverviewMode = true
                // 其他设置...
            }
        }
    }

    protected open fun loadUrl() {
        url?.let { webView?.loadUrl(it) }
    }

    override fun onResume() {
        super.onResume()
        webView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        webView?.onPause()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        webView?.destroy()
        webView = null
    }


    fun canGoBack(): Boolean {
        return webView?.canGoBack() == true
    }

    fun goBack() {
        webView?.goBack()
    }
} 