package com.example.lib_spider.engine

import com.example.lib_spider.model.RuleConfig
import com.example.lib_spider.model.VideoItem

interface SpiderEngine {
    fun fetchData(ruleConfig: RuleConfig): List<VideoItem>
} 