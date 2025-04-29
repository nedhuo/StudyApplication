package com.example.lib_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.lib_database.entity.TvBoxSiteEntity

@Dao
interface TvBoxSiteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSites(sites: List<TvBoxSiteEntity>)

    @Query("SELECT * FROM tvbox_site")
    suspend fun getAllSites(): List<TvBoxSiteEntity>
} 