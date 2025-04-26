package com.example.feature_tvbox.player

import androidx.lifecycle.ViewModel
import com.example.lib_player.core.PlayerManager

class TvBoxPlayerViewModel : ViewModel() {
    private val player = PlayerManager.getPlayer()

    fun play(url: String) {
        player.play(url)
    }

    fun pause() {
        player.pause()
    }

    fun stop() {
        player.stop()
    }

    override fun onCleared() {
        super.onCleared()
        PlayerManager.releasePlayer()
    }
} 