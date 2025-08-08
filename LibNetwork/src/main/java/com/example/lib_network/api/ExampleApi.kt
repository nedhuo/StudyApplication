package com.example.lib_network.api

import retrofit2.http.*

/**
 * 示例API接口
 */
interface ExampleApi {
    @GET("users/{userId}")
    suspend fun getUser(@Path("userId") userId: String): User
    
    @POST("users")
    suspend fun createUser(@Body user: User): User
    
    @PUT("users/{userId}")
    suspend fun updateUser(@Path("userId") userId: String, @Body user: User): User
    
    @DELETE("users/{userId}")
    suspend fun deleteUser(@Path("userId") userId: String)
    
    @GET("users")
    suspend fun getUsers(@Query("page") page: Int, @Query("size") size: Int): List<User>
}

/**
 * 示例用户数据类
 */
data class User(
    val id: String,
    val name: String,
    val email: String
)