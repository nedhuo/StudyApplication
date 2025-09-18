package com.nedhuo.libnetwork

import com.google.gson.JsonParseException
import com.nedhuo.libnetwork.exception.ApiException
import com.nedhuo.log.LogUtils
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertificateException

object CommonErrorHandler {


    fun mapException(throwable: Throwable) {
        when (throwable) {
            is ApiException -> {

            }

            is HttpException -> {
                val errorBody = throwable.response()?.errorBody()?.string()
                val errorMessage = when {
                    errorBody.isNullOrEmpty() -> throwable.message()
                    else -> "${throwable.message()}: $errorBody"
                }

                when (throwable.code()) {
                    401 -> {

                    }

                    in 400..499 -> {

                    }

                    in 500..599 -> {

                    }

                    else -> {

                    }
                }
            }

            is SocketTimeoutException -> {

            }

            is UnknownHostException -> {

            }

            is JsonParseException -> {

            }

            is ConnectException -> {

            }

            is CertificateException -> {

            }

            else -> {

            }
        }
    }
}