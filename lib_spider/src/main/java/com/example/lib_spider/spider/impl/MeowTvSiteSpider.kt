package com.example.lib_spider.spider.impl

import com.example.lib_database.entity.TvBoxSiteEntity
import com.example.lib_database.entity.VideoEntity
import com.example.lib_spider.spider.api.BaseSiteSpider
import com.example.lib_spider.spider.parser.MeowTvHtmlParser

class MeowTvSiteSpider : BaseSiteSpider {
    suspend fun fetchVideos(site: TvBoxSiteEntity, keyword: String?): List<VideoEntity> {
        return try {
            val html = ApiClient // 这里应有网络请求方法, 你可补充 getHtml(url) 等
            val parser = MeowTvHtmlParser()
            parser.parseHomePage(html.toString()).map {
                VideoEntity(
                    url = it.api,
                    title = it.name,
                    cover = "" // 你可根据实际情况补充封面字段
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
} 