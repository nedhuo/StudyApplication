package com.example.feature_login.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.feature_login.data.model.LoginResponse
import com.example.feature_login.domain.usecase.LoginUseCase
import com.example.lib_base.base.BaseViewModel
import com.example.lib_base.state.ViewState
import com.example.lib_base.state.ViewState.*
import com.nedhuo.libnetwork.result.NetworkResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : BaseViewModel() {
    
    private val _loginState = MutableLiveData<ViewState<LoginResponse>>()
    val loginState: LiveData<ViewState<LoginResponse>> = _loginState
    
    fun login(username: String, password: String) {
        viewModelScope.launch {
            _loginState.value = ViewState.Loading
            
            val result = loginUseCase(username, password)
            _loginState.value = when (result) {
                is NetworkResponse.Success -> Success(result.data)
                is NetworkResponse.Error -> Error(0,result.message)
                is NetworkResponse.Exception -> Exception(result.e)
                NetworkResponse.Loading -> Loading
            }
        }
    }
}