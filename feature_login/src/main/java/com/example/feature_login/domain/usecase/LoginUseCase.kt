package com.example.feature_login.domain.usecase

import com.example.feature_login.data.model.LoginResponse
import com.example.feature_login.domain.repository.LoginRepository
import com.example.lib_network.model.Result
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: LoginRepository
) {
    suspend operator fun invoke(username: String, password: String): Result<LoginResponse> {
        // 这里可以添加业务逻辑，比如参数校验
        if (username.isBlank()) {
            return Result.Error("用户名不能为空")
        }
        if (password.isBlank()) {
            return Result.Error("密码不能为空")
        }
        if (password.length < 6) {
            return Result.Error("密码长度不能小于6位")
        }
        
        return repository.login(username, password)
    }
}