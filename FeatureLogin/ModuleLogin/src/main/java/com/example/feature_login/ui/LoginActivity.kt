package com.example.feature_login.ui

import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import com.example.feature_login.databinding.ActivityLoginBinding
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.ext.bindings
import com.example.lib_base.state.ViewState


class LoginActivity : BaseActivity() {

    private val viewModel: LoginViewModel by viewModels()
    private val binding by bindings<ActivityLoginBinding>()


    override fun initView() {
    }

    override fun initData() {
        viewModel.loginState.observe(this) { state ->
            when (state) {
                is ViewState.Loading -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.loginButton.isEnabled = false
                }

                is ViewState.Success -> {
                    binding.progressBar.visibility = View.GONE
                    binding.loginButton.isEnabled = true
                    // 登录成功，可以保存用户信息，跳转到主页等
                    Toast.makeText(this, "登录成功", Toast.LENGTH_SHORT).show()
                    finish()
                }

                is ViewState.Error -> {
                    binding.progressBar.visibility = View.GONE
                    binding.loginButton.isEnabled = true
                    Toast.makeText(this, state.message, Toast.LENGTH_SHORT).show()
                }

                is ViewState.Exception -> {
                    binding.progressBar.visibility = View.GONE
                    binding.loginButton.isEnabled = true
                    Toast.makeText(this, "发生错误：${state.throwable.message}", Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }


}