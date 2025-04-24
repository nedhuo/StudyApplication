package com.example.lib_database.dao

import androidx.room.*
import com.example.lib_database.entity.PlayHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface PlayHistoryDao {
    @Query("SELECT * FROM play_history ORDER BY updateTime DESC")
    fun getAllHistory(): Flow<List<PlayHistory>>

    @Query("SELECT * FROM play_history WHERE sourceId = :sourceId")
    fun getHistoryBySource(sourceId: String): Flow<List<PlayHistory>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHistory(history: PlayHistory)

    @Delete
    suspend fun deleteHistory(history: PlayHistory)

    @Query("DELETE FROM play_history")
    suspend fun clearHistory()
} 