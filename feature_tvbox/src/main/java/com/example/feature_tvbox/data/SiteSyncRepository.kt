package com.example.feature_tvbox.data

import com.example.lib_database.DatabaseManager
import com.example.lib_spider.meowtv.spider.MeowTvSpider
import com.example.lib_spider.meowtv.parser.MeowTvSiteParser

class SiteSyncRepository(
    private val databaseManager: DatabaseManager,
    private val spider: MeowTvSpider
) {
    /**
     * 拉取主站 JSON，解析站点并保存到数据库
     * http://ztha.top/TVBox/thdjk.json
     * http://www.meowtv.top
     */
    suspend fun syncSitesFromRemote(url: String = "http://ztha.top/TVBox/thdjk.json") {
        // 1. 拉取 JSON
        val json = spider.fetchMeowTvSitesJson(url)
        // 2. 解析为站点实体
        val siteList = MeowTvSiteParser.parseSites(json)
        // 3. 存入数据库
        databaseManager.tvBoxSiteDao.insertSites(siteList)
    }
} 