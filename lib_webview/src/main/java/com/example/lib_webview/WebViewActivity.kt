package com.example.lib_webview

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.lib_webview.fragment.BaseWebViewFragment

/**
 * WebView Activity
 */
class WebViewActivity : AppCompatActivity() {
    private var webViewFragment: BaseWebViewFragment? = null

    companion object {
        private const val EXTRA_URL = "extra_url"
        private const val EXTRA_TITLE = "extra_title"

        fun start(context: Context, url: String, title: String? = null) {
            context.startActivity(Intent(context, WebViewActivity::class.java).apply {
                putExtra(EXTRA_URL, url)
                putExtra(EXTRA_TITLE, title)
            })
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)

        val url = intent.getStringExtra(EXTRA_URL) ?: return
        val title = intent.getStringExtra(EXTRA_TITLE)

        if (savedInstanceState == null) {
            webViewFragment = BaseWebViewFragment.newInstance(url, title)
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, webViewFragment!!)
                .commit()
        }
    }

    override fun onBackPressed() {
        if (webViewFragment?.canGoBack() == true) {
            webViewFragment?.goBack()
        } else {
            super.onBackPressed()
        }
    }
} 