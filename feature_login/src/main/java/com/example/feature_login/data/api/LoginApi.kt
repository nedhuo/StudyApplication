package com.example.feature_login.data.api

import com.example.feature_login.data.model.LoginRequest
import com.example.feature_login.data.model.LoginResponse
import com.example.lib_network.result.NetworkResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface LoginApi {
    @POST("api/login")
    suspend fun login(@Body request: LoginRequest): NetworkResponse<LoginResponse>
}