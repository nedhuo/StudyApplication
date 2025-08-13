package com.example.lib_base.base

interface IStateView {

    fun showLoading(content: String? = null)


    fun dismissLoading()
}