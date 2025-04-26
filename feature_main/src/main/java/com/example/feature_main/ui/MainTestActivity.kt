package com.example.feature_main.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.feature_main.databinding.ActivityMainTestBinding

class MainTestActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainTestBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainTestBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setupViews()
    }
    
    private fun setupViews() {
        binding.btnOpenMain.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
} 