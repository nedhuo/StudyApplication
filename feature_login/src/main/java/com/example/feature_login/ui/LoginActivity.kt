package com.example.feature_login.ui

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.viewModels
import com.example.feature_login.R
import com.example.feature_login.databinding.ActivityLoginBinding
import com.example.lib_base.base.BaseActivity
import com.example.lib_base.state.ViewState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginActivity : BaseActivity<ActivityLoginBinding>() {
    
    private val viewModel: LoginViewModel by viewModels()
    
    override fun getViewBinding() = ActivityLoginBinding.inflate(layoutInflater)
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        setupViews()
        observeViewModel()
    }
    
    private fun setupViews() {
        binding.loginButton.setOnClickListener {
            val username = binding.usernameEdit.text.toString()
            val password = binding.passwordEdit.text.toString()
            viewModel.login(username, password)
        }

        LinearLayout
    }
    
    private fun observeViewModel() {
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
                    Toast.makeText(this, "发生错误：${state.e.message}", Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }
    }
}