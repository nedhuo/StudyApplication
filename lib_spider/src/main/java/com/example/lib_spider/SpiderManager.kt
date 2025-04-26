package com.example.lib_spider

import com.example.lib_spider.factory.SpiderFactory
import com.example.lib_spider.model.VideoItem

object SpiderManager {
    fun parseAndFetch(type: String, fileContent: String): List<VideoItem> {
        val parser = SpiderFactory.getRuleParser(type)
        val ruleConfig = parser.parseRuleFile(fileContent)
        val spider = SpiderFactory.getSpiderEngine(type)
        return spider.fetchData(ruleConfig)
    }
} 