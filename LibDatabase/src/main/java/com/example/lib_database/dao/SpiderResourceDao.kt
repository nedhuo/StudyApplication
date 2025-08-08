package com.example.lib_database.dao

import androidx.room.*
import com.example.lib_database.entity.VideoEntity

@Dao
interface SpiderResourceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertResource(video: VideoEntity)

    @Query("SELECT * FROM video")
    suspend fun getAllResources(): List<VideoEntity>
} 