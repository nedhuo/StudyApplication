package com.example.lib_network.example

import com.example.lib_network.api.ExampleApi
import com.example.lib_network.api.User
import com.example.lib_network.result.Result
import com.example.lib_network.result.apiCall
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Flow

/**
 * 用户仓库示例
 */
class UserRepository(private val api: ExampleApi) {
    
    /**
     * 获取用户信息ΩΩ
     */
    fun getUser(userId: String): Flow<Result<User>> = apiCall {
        api.getUser(userId)
    }
    
    /**
     * 获取用户列表
     */
    fun getUsers(page: Int, size: Int): Flow<Result<List<User>>> = apiCall {
        api.getUsers(page, size)
    }
    
    /**
     * 创建用户
     */
    fun createUser(user: User): Flow<Result<User>> = apiCall {
        api.createUser(user)
    }
    
    /**
     * 更新用户
     */
    fun updateUser(userId: String, user: User): Flow<Result<User>> = apiCall {
        api.updateUser(userId, user)
    }
    
    /**
     * 删除用户
     */
    fun deleteUser(userId: String): Flow<Result<Unit>> = apiCall {
        api.deleteUser(userId)
    }
}