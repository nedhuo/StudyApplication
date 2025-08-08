package com.example.feature_login.domain.repository

import com.example.feature_login.data.model.LoginResponse
import com.example.lib_network.result.NetworkResponse

interface LoginRepository {
    suspend fun login(username: String, password: String): NetworkResponse<LoginResponse>
}