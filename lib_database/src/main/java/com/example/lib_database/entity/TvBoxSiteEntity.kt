package com.example.lib_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tvbox_site")
data class TvBoxSiteEntity(
    @PrimaryKey val key: String,
    val name: String,
    val type: Int = 3,
    val api: String,
    val indexs: Int? = null,
    val searchable: Int? = null,
    val quickSearch: Int? = null,
    val filterable: Int? = null,
    val changeable: Int? = null,
    val playerType: Int? = null,
    val timeout: Int? = null,
    val jar: String? = null,
    val ext: String? = null
) 