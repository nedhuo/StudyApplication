package com.example.lib_config.ui

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import com.example.LibConfig.R
import com.example.LibConfig.databinding.DialogEnvironmentSwitchBinding
import com.example.lib_config.core.AppConfig
import com.example.lib_config.model.Environment

class EnvironmentSwitchDialog(
    context: Context,
    private val onEnvironmentChanged: () -> Unit
) : Dialog(context) {

    private lateinit var binding: DialogEnvironmentSwitchBinding
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DialogEnvironmentSwitchBinding.inflate(LayoutInflater.from(context))
        setContentView(binding.root)
        
        initViews()
    }
    
    private fun initViews() {
        // 设置当前环境
        when (AppConfig.currentEnvironment.value) {
            Environment.DEV -> binding.rbDev.isChecked = true
            Environment.STAGING -> binding.rbStaging.isChecked = true
            Environment.PROD -> binding.rbProd.isChecked = true
        }
        
        // 取消按钮
        binding.btnCancel.setOnClickListener {
            dismiss()
        }
        
        // 确认按钮
        binding.btnConfirm.setOnClickListener {
            val selectedEnvironment = when (binding.environmentGroup.checkedRadioButtonId) {
                R.id.rbDev -> Environment.DEV
                R.id.rbStaging -> Environment.STAGING
                R.id.rbProd -> Environment.PROD
                else -> return@setOnClickListener
            }
            
            if (selectedEnvironment != AppConfig.currentEnvironment.value) {
                AppConfig.switchEnvironment(selectedEnvironment)
                onEnvironmentChanged()
            }
            dismiss()
        }
    }
}