package com.example.feature_tvbox

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.feature_tvbox.list.TvBoxListFragment
import com.example.lib_base.widget.LoadingDialog
import kotlinx.coroutines.launch

class TvBoxMainActivity : AppCompatActivity() {
    private var loadingDialog: LoadingDialog? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 可选：setContentView(R.layout.activity_tvbox_main)
        showLoading()
        lifecycleScope.launch {
            hideLoading()
            switchToFragment(TvBoxListFragment())
        }
    }

    private fun showLoading() {
        if (loadingDialog == null) {
            loadingDialog = LoadingDialog()
            loadingDialog?.show(supportFragmentManager, "loading")
        }
    }

    private fun hideLoading() {
        loadingDialog?.dismiss()
        loadingDialog = null
    }

    private fun switchToFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(android.R.id.content, fragment)
            .commitAllowingStateLoss()
    }
}
