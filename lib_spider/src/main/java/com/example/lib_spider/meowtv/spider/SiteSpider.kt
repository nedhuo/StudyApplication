package com.example.lib_spider.meowtv.spider

import com.example.lib_database.entity.TvBoxSiteEntity
import com.example.lib_database.entity.VideoEntity

interface SiteSpider {
    suspend fun fetchVideos(site: TvBoxSiteEntity, keyword: String? = null): List<VideoEntity>
} 