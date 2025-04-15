package com.example.lib_network.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_network.api.User
import com.example.lib_network.result.Result
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

/**
 * 用户ViewModel示例
 */
class UserViewModel(
    private val repository: UserRepository
) : ViewModel() {
    
    private val _userState = MutableStateFlow<Result<User>>(Result.Loading)
    val userState: StateFlow<Result<User>> = _userState
    
    private val _usersState = MutableStateFlow<Result<List<User>>>(Result.Loading)
    val usersState: StateFlow<Result<List<User>>> = _usersState
    
    /**
     * 获取用户信息
     */
    fun getUser(userId: String) {
        repository.getUser(userId)
            .onEach { result -> _userState.value = result }
            .launchIn(viewModelScope)
    }
    
    /**
     * 获取用户列表
     */
    fun getUsers(page: Int = 1, size: Int = 20) {
        repository.getUsers(page, size)
            .onEach { result -> _usersState.value = result }
            .launchIn(viewModelScope)
    }
    
    /**
     * 创建用户
     */
    fun createUser(name: String, email: String) {
        val user = User(id = "", name = name, email = email)
        repository.createUser(user)
            .onEach { result -> _userState.value = result }
            .launchIn(viewModelScope)
    }
    
    /**
     * 更新用户
     */
    fun updateUser(userId: String, name: String, email: String) {
        val user = User(id = userId, name = name, email = email)
        repository.updateUser(userId, user)
            .onEach { result -> _userState.value = result }
            .launchIn(viewModelScope)
    }
    
    /**
     * 删除用户
     */
    fun deleteUser(userId: String) {
        repository.deleteUser(userId)
            .onEach { result ->
                when (result) {
                    is Result.Success -> _userState.value = Result.Success(User("", "", ""))
                    is Result.Error -> _userState.value = Result.Error(result.code, result.message)
                    is Result.Exception -> _userState.value = Result.Exception(result.e)
                    Result.Loading -> _userState.value = Result.Loading
                }
            }
            .launchIn(viewModelScope)
    }
}