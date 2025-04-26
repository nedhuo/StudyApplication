package com.example.lib_spider.engine

import com.example.lib_spider.model.RuleConfig
import com.example.lib_spider.model.VideoItem

class TvBoxSpiderEngine : SpiderEngine {
    override fun fetchData(ruleConfig: RuleConfig): List<VideoItem> {
        // 简单示例，实际需根据规则抓取数据
        return listOf(
            VideoItem("演示视频", "http://example.com/video.mp4", "http://example.com/cover.jpg")
        )
    }
} 