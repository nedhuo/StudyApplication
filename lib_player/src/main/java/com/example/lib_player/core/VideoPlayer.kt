package com.example.lib_player.core

interface VideoPlayer {
    fun play(url: String)
    fun pause()
    fun stop()
    fun release()
} 