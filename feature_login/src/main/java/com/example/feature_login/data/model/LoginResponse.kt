package com.example.feature_login.data.model

data class LoginResponse(
    val token: String,
    val userId: String,
    val username: String,
    val nickname: String?,
    val avatar: String?
)