package com.nedhuo.libnetwork.ext

import android.widget.Toast
import com.google.gson.JsonParseException
import com.nedhuo.libnetwork.exception.ApiException
import com.nedhuo.libutils.utilcode.util.LogUtils
import com.nedhuo.libutils.utilcode.util.ToastUtils
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import java.security.cert.CertificateException


fun Throwable.mapException(interceptor: ((Throwable) -> Boolean)? = null) {
    if (interceptor?.invoke(this) == true) return
    when (this) {
        is ApiException -> {
            LogUtils.e("mapException ApiException: errorCode=${code} erroeMsg=$message")
            ToastUtils.showShort(message)
        }

        is HttpException -> {
            val errorBody = this.response()?.errorBody()?.string()
            when {
                errorBody.isNullOrEmpty() -> this.message()
                else -> "${this.message()}: $errorBody"
            }

            when (this.code()) {
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