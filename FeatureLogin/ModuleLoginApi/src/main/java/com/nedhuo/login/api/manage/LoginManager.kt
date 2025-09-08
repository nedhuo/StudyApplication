package com.nedhuo.login.api.manage

import kotlinx.coroutines.flow.MutableStateFlow

class LoginManager {

    private val loginStatusFlow = MutableStateFlow(false)


    fun updateLoginStatus(isLogin: Boolean? = null) {
        if (isLogin != null) {
            loginStatusFlow.value = isLogin
            return
        }
        loginStatusFlow.value = !UserInfoManager.getInstance().getUserInfo()?.userId.isNullOrEmpty()
    }

    fun isLogin(): Boolean {
        return loginStatusFlow.value
    }

    fun getLoginStatusFlow(): MutableStateFlow<Boolean> {
        return loginStatusFlow
    }

    companion object {
        private val instance: LoginManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { LoginManager() }

        @JvmStatic
        fun getInstance(): LoginManager {
            return instance
        }
    }
}