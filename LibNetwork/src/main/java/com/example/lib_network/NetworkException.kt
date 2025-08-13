package com.example.lib_network

/**
 * 网络异常封装
 */
sealed class NetworkException : Exception {
    constructor(message: String? = null, cause: Throwable? = null) : super(message, cause)

    class HttpError(message: String? = null, cause: Throwable? = null) : NetworkException(message)

    class NetworkError(message: String? = null, cause: Throwable? = null) : NetworkException(cause = cause)

    class ServerError(message: String? = null, cause: Throwable? = null) : NetworkException(message)

    class UnauthorizedError(message: String? = null, cause: Throwable? = null) : NetworkException(message)

    class TimeoutError(message: String? = null, cause: Throwable? = null) : NetworkException(message)

    class ParseError(message: String? = null, cause: Throwable? = null) : NetworkException(cause = cause)
}