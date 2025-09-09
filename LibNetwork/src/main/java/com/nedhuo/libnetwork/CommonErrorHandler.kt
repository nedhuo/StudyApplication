package com.nedhuo.libnetwork

import com.google.gson.JsonParseException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertificateException

object CommonErrorHandler {

    fun mapApiError(code: Int, message: String) {

    }

    fun mapApiError(code: Int, message: String, handleMap: Map<Int, (String) -> Unit>) {
        handleMap[code]?.invoke(message) ?: mapApiError(code, message)
    }

    fun mapException(throwable: Throwable): NetworkException {
        return when (throwable) {
            is HttpException -> {
                val errorBody = throwable.response()?.errorBody()?.string()
                val errorMessage = when {
                    errorBody.isNullOrEmpty() -> throwable.message()
                    else -> "${throwable.message()}: $errorBody"
                }

                when (throwable.code()) {
                    401 -> NetworkException.UnauthorizedError()
                    in 400..499 -> NetworkException.HttpError(errorMessage, throwable)
                    in 500..599 -> NetworkException.ServerError(errorMessage, throwable)
                    else -> NetworkException.HttpError(errorMessage, throwable)
                }
            }

            is SocketTimeoutException -> NetworkException.TimeoutError("Request timed out", throwable)
            is UnknownHostException -> NetworkException.NetworkError("Unknown host: please check your network connection", throwable)
            is JsonParseException -> NetworkException.ParseError("Failed to parse response data", throwable)
            is ConnectException -> NetworkException.NetworkError("Failed to connect to server", throwable)
            is CertificateException -> NetworkException.NetworkError("SSL certificate error", throwable)
            else -> NetworkException.NetworkError("Network error occurred", throwable)
        }
    }
}