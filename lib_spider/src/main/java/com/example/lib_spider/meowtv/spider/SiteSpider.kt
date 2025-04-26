package com.example.lib_spider.meowtv.spider

import com.example.lib_database.entity.SiteEntity
import com.example.lib_database.entity.VideoEntity

interface SiteSpider {
    suspend fun fetchVideos(site: SiteEntity, keyword: String? = null): List<VideoEntity>
} 