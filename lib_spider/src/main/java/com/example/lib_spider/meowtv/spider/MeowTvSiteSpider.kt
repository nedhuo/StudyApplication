package com.example.lib_spider.meowtv.spider

import com.example.lib_database.entity.SiteEntity
import com.example.lib_database.entity.VideoEntity
import com.example.lib_database.dao.VideoDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object MeowTvSiteSpider {
    // 这里只是示例，实际应根据 site.api、ext、jar 等动态加载爬虫并抓取数据
    suspend fun fetchAndSaveVideos(site: SiteEntity, videoDao: VideoDao, keyword: String? = null) {
        val spider = SiteSpiderFactory.getSpider(site)
        val videos = spider.fetchVideos(site, keyword)
        withContext(Dispatchers.IO) {
            videos.forEach { videoDao.insertVideo(it) }
        }
    }
} 