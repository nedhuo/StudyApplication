package com.example.feature_login.domain.repository

import com.example.feature_login.data.model.LoginResponse
import com.nedhuo.libnetwork.NetworkManager
import com.nedhuo.libnetwork.result.BaseResponse
import com.nedhuo.libnetwork.result.NetworkResponse

class LoginRepository private constructor() {
    private val service = NetworkManager.createService(LoginApi::class.java)


    suspend fun login(username: String, password: String): BaseResponse<LoginResponse> {
        return service.login(username, password)
    }

    companion object {
        private val repository by lazy { LoginRepository() }

        @JvmStatic
        fun getInstance(): LoginRepository {
            return repository
        }
    }
}