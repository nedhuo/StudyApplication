package com.example.lib_spider.meowtv.spider

import com.example.lib_database.entity.TvBoxSiteEntity
import com.example.lib_database.entity.VideoEntity

class DefaultSpider : SiteSpider {
    override suspend fun fetchVideos(site: TvBoxSiteEntity, keyword: String?): List<VideoEntity> {
        // 返回空列表或日志提示
        return emptyList()
    }
} 