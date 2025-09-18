package com.example.feature_login.domain.repository

import com.example.feature_login.data.model.LoginResponse
import com.nedhuo.libnetwork.result.BaseResponse
import com.nedhuo.libnetwork.result.NetworkResponse

interface LoginApi {

    suspend fun login(username: String, password: String): BaseResponse<LoginResponse>
}