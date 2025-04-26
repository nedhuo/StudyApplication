package com.example.lib_database.dao

import androidx.room.*
import com.example.lib_database.entity.SiteEntity

@Dao
interface SiteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSites(sites: List<SiteEntity>)

    @Query("SELECT * FROM site")
    suspend fun getAllSites(): List<SiteEntity>
} 