package com.example.lib_spider.factory

import com.example.lib_spider.parser.RuleParser
import com.example.lib_spider.parser.TvBoxRuleParser
import com.example.lib_spider.engine.SpiderEngine
import com.example.lib_spider.engine.TvBoxSpiderEngine

object SpiderFactory {
    fun getRuleParser(type: String): RuleParser = when(type) {
        "tvbox" -> TvBoxRuleParser()
        else -> throw IllegalArgumentException("未知规则类型")
    }
    fun getSpiderEngine(type: String): SpiderEngine = when(type) {
        "tvbox" -> TvBoxSpiderEngine()
        else -> throw IllegalArgumentException("未知爬虫类型")
    }
} 