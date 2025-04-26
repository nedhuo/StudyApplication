package com.example.lib_player.core

object PlayerManager {
    private var player: VideoPlayer? = null

    fun getPlayer(): VideoPlayer {
        if (player == null) {
            player = ExoVideoPlayer()
        }
        return player!!
    }

    fun releasePlayer() {
        player?.release()
        player = null
    }
} 