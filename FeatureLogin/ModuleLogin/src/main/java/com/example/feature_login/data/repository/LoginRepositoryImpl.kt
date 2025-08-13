package com.example.feature_login.data.repository

import com.example.feature_login.data.api.LoginApi
import com.example.feature_login.data.model.LoginRequest
import com.example.feature_login.data.model.LoginResponse
import com.example.feature_login.domain.repository.LoginRepository
import com.nedhuo.libnetwork.result.NetworkResponse
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val loginApi: LoginApi
) : LoginRepository {

    override suspend fun login(username: String, password: String): NetworkResponse<LoginResponse> {
        return loginApi.login(LoginRequest(username, password))
    }
}