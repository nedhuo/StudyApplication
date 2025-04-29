package com.example.lib_spider.spider.factory

import com.example.lib_database.entity.TvBoxSiteEntity
import com.example.lib_spider.spider.api.BaseSiteSpider
import com.example.lib_spider.spider.impl.MeowTvSiteSpider
import com.example.lib_spider.spider.impl.NmvodSpider
import com.example.lib_spider.spider.impl.DefaultSpider

object SiteSpiderFactory {
    fun getSpider(site: TvBoxSiteEntity): BaseSiteSpider {
        // 可根据 site.api 字段动态返回不同 Spider
        // 这里只做示例，实际可扩展更多类型
        return when {
            site.api.contains("Nmvod", ignoreCase = true) -> NmvodSpider()
            site.api.contains("TTian", ignoreCase = true) -> DefaultSpider()
            else -> MeowTvSiteSpider()
        }
    }
} 