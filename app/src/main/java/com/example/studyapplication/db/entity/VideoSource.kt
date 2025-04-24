package com.example.studyapplication.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "video_source")
data class VideoSource(
    @PrimaryKey
    val id: String,
    val name: String,
    val type: Int,
    val api: String,
    val searchable: Int,
    val quickSearch: Int,
    val filterable: Int,
    val ext: String?,
    val jar: String?,
    val playerType: Int,
    val categories: String?,
    val clickSelector: String?,
    val timeout: Int,
    val updateTime: Long = System.currentTimeMillis()
) 