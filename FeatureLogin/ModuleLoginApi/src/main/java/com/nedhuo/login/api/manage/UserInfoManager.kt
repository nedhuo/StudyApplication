package com.nedhuo.login.api.manage

import com.nedhuo.login.api.data.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow

class UserInfoManager {

    private val userInfoFlow = MutableStateFlow<UserInfo?>(null)
    private var userInfo: UserInfo? = null

    fun updateUserInfo(userInfo: UserInfo) {
        this.userInfo = UserInfo(userInfo.nickname, userInfo.avatar, userInfo.userId)
        userInfoFlow.value = userInfo
    }

    fun getUserInfo(): UserInfo? {
        return userInfo
    }

    fun getUserInfoFlow(): MutableStateFlow<UserInfo?> {
        return userInfoFlow
    }

    companion object {

        private val instance: UserInfoManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) { UserInfoManager() }

        @JvmStatic
        fun getInstance(): UserInfoManager {
            return instance
        }
    }

}