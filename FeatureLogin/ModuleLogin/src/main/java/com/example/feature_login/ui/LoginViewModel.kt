package com.example.feature_login.ui

import androidx.constraintlayout.motion.utils.ViewState
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.feature_login.data.model.LoginResponse
import com.example.feature_login.domain.repository.LoginRepository
import com.example.lib_base.base.BaseViewModel
import com.example.lib_base.ext.dataOrThrow
import com.example.lib_base.state.ViewState
import com.nedhuo.libnetwork.ext.mapException
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginRepository
) : BaseViewModel() {
    private val loginRepository by lazy { LoginRepository.getInstance() }

    private val _loginState = MutableLiveData<ViewState<LoginResponse>>()
    val loginState: LiveData<ViewState<LoginResponse>> = _loginState

    MutableSharedFlow<>()

    fun login(username: String, password: String) {
        launchWithLoading(onError = {
            it.mapException({
                return@mapException false
            })
        }) {
            val dataOrThrow = loginRepository.login(username, password).dataOrThrow()
        }
    }
}