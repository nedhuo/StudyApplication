package com.example.lib_network.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_network.api.User
import com.example.lib_network.result.NetworkResponse
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
    
    private val _userState = MutableStateFlow<NetworkResponse<User>>(NetworkResponse.Loading)
    val userState: StateFlow<NetworkResponse<User>> = _userState
    
    private val _usersState = MutableStateFlow<NetworkResponse<List<User>>>(NetworkResponse.Loading)
    val usersState: StateFlow<NetworkResponse<List<User>>> = _usersState
    
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
                    is NetworkResponse.Success -> _userState.value = NetworkResponse.Success(User("", "", ""))
                    is NetworkResponse.Error -> _userState.value = NetworkResponse.Error(result.code, result.message)
                    is NetworkResponse.Exception -> _userState.value = NetworkResponse.Exception(result.e)
                    NetworkResponse.Loading -> _userState.value = NetworkResponse.Loading
                }
            }
            .launchIn(viewModelScope)
    }
}