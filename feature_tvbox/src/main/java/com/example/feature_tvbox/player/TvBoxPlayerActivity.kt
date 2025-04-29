package com.example.feature_tvbox.player

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.feature_tvbox.databinding.ActivityTvboxPlayerBinding
import android.widget.Toast
import android.net.Uri
import android.widget.MediaController
import android.widget.VideoView

class TvBoxPlayerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTvboxPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvboxPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val url = intent.getStringExtra(EXTRA_URL)
        val title = intent.getStringExtra(EXTRA_TITLE)
        if (url.isNullOrEmpty()) {
            Toast.makeText(this, "无效播放地址", Toast.LENGTH_SHORT).show()
            finish()
            return
        }
        binding.tvTitle.text = title ?: ""
        val videoView: VideoView = binding.videoView
        videoView.setVideoURI(Uri.parse(url))
        videoView.setMediaController(MediaController(this))
        videoView.requestFocus()
        videoView.start()
    }

    companion object {
        private const val EXTRA_URL = "url"
        private const val EXTRA_TITLE = "title"
        fun newIntent(context: Context, url: String, title: String): Intent {
            return Intent(context, TvBoxPlayerActivity::class.java).apply {
                putExtra(EXTRA_URL, url)
                putExtra(EXTRA_TITLE, title)
            }
        }
    }
} 