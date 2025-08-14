package com.example.lib_base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * 一个通用的 ViewModelProvider.Factory，通过一个 lambda 表达式来创建 ViewModel。
 *
 * @param create 传入一个 lambda，负责创建 ViewModel 实例。
 */
class GenericViewModelFactory<T : ViewModel>(private val create: () -> T) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return try {
            create() as T
        } catch (e: Exception) {
            throw IllegalArgumentException("ViewModel of type ${modelClass.simpleName} could not be created.", e)
        }
    }
}