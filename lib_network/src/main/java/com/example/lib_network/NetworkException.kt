package com.example.lib_network

/**
 * 网络异常封装
 */
sealed class NetworkException : Exception() {
    data class HttpError(
        val code: Int,
        override val message: String,
        val body: String? = null
    ) : NetworkException()
    
    data class NetworkError(
        override val cause: Throwable
    ) : NetworkException()
    
    data class ServerError(
        val errorCode: Int,
        override val message: String
    ) : NetworkException()
    
    data class UnauthorizedError(
        override val message: String = "Unauthorized"
    ) : NetworkException()
    
    data class TimeoutError(
        override val message: String = "Request timeout"
    ) : NetworkException()
    
    data class ParseError(
        override val cause: Throwable
    ) : NetworkException()
}