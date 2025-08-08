package com.example.lib_database.dao

import androidx.room.*
import com.example.lib_database.entity.VideoEntity

@Dao
interface VideoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertVideo(video: VideoEntity)

    @Query("SELECT * FROM video WHERE url = :url LIMIT 1")
    suspend fun getVideoByUrl(url: String): VideoEntity?

    @Query("SELECT * FROM video")
    suspend fun getAllVideos(): List<VideoEntity>

    @Delete
    suspend fun deleteVideo(video: VideoEntity)
} 