package com.example.lib_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video")
data class VideoEntity(
    @PrimaryKey val url: String,
    val title: String,
    val cover: String
) 