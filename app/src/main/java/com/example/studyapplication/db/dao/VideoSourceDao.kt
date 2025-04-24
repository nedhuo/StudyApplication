package com.example.studyapplication.db.dao

import androidx.room.*
import com.example.studyapplication.db.entity.VideoSource
import kotlinx.coroutines.flow.Flow

@Dao
interface VideoSourceDao {
    @Query("SELECT * FROM video_source")
    fun getAllSources(): Flow<List<VideoSource>>

    @Query("SELECT * FROM video_source WHERE type = :type")
    fun getSourcesByType(type: Int): Flow<List<VideoSource>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSource(source: VideoSource)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSources(sources: List<VideoSource>)

    @Delete
    suspend fun deleteSource(source: VideoSource)

    @Query("DELETE FROM video_source")
    suspend fun deleteAllSources()
} 