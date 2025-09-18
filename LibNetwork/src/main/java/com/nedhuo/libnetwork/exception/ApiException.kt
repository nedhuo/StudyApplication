package com.nedhuo.libnetwork.exception

class ApiException @JvmOverloads constructor(code: Int?, message: String? = null) : Exception() {
    var code: Int = DEFAULT_CODE

    init {
        this.code = code ?: DEFAULT_CODE
        this.message = message
    }

    fun getMessage(): String? {
        return message
    }

    companion object {
        const val DEFAULT_CODE = -1
    }
}