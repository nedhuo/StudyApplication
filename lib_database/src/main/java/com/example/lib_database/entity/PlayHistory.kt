package com.example.lib_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "play_history")
data class PlayHistory(
    @PrimaryKey
    val id: String,
    val title: String,
    val sourceId: String,
    val episodeIndex: Int,
    val playPosition: Long,
    val duration: Long,
    val updateTime: Long = System.currentTimeMillis()
) 