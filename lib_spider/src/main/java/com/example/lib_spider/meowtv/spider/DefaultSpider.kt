package com.example.lib_spider.meowtv.spider

import com.example.lib_database.entity.SiteEntity
import com.example.lib_database.entity.VideoEntity

class DefaultSpider : SiteSpider {
    override suspend fun fetchVideos(site: SiteEntity, keyword: String?): List<VideoEntity> {
        // 返回空列表或日志提示
        return emptyList()
    }
} 