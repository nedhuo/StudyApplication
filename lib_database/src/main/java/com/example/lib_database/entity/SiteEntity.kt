package com.example.lib_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "site")
data class SiteEntity(
    @PrimaryKey val key: String,
    val name: String,
    val api: String,
    val jar: String?,
    val ext: String?
) 