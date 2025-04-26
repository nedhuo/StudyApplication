package com.example.lib_player.core

import android.util.Log

class ExoVideoPlayer : VideoPlayer {
    override fun play(url: String) {
        Log.d("ExoVideoPlayer", "play: $url")
        // 实际播放逻辑集成 ExoPlayer
    }
    override fun pause() {
        Log.d("ExoVideoPlayer", "pause")
    }
    override fun stop() {
        Log.d("ExoVideoPlayer", "stop")
    }
    override fun release() {
        Log.d("ExoVideoPlayer", "release")
    }
} 