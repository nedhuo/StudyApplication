package com.example.lib_network

/**
 * 网络异常封装
 */
sealed class NetworkException : Exception {
    constructor(message: String? = null, cause: Throwable? = null) : super(message, cause)

    class HttpError(
        val code: Int,
        message: String?,
        val body: String? = null
    ) : NetworkException(message)
    
    class NetworkError(
        cause: Throwable
    ) : NetworkException(cause = cause)
    
    class ServerError(
        val errorCode: Int,
        message: String?
    ) : NetworkException(message)
    
    class UnauthorizedError(
        message: String = "Unauthorized"
    ) : NetworkException(message)
    
    class TimeoutError(
        message: String = "Request timeout"
    ) : NetworkException(message)
    
    class ParseError(
        cause: Throwable
    ) : NetworkException(cause = cause)
}