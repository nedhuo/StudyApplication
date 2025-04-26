package com.example.lib_spider.meowtv.spider

import com.example.lib_database.entity.SiteEntity

object SiteSpiderFactory {
    fun getSpider(site: SiteEntity): SiteSpider {
        return when (site.api) {
            "csp_Nmvod" -> NmvodSpider()
            // 可继续扩展其他类型
            else -> DefaultSpider()
        }
    }
} 