package com.example.feature_tvbox.data

import com.example.lib_database.DatabaseManager
import com.example.lib_spider.meowtv.spider.MeowTvSpider
import kotlinx.coroutines.flow.Flow
import com.example.lib_database.entity.VideoSource

class TvBoxRepository(
    private val databaseManager: DatabaseManager,
    private val spider: MeowTvSpider
) {
    fun getAllVideos(): Flow<List<VideoSource>> = databaseManager.getAllVideoSources()
    suspend fun fetchAndSaveMeowTvSites() {
        val json = spider.fetchMeowTvSitesJson("http://www.meowtv.top")
        databaseManager.importFromJson(json)
    }
} 