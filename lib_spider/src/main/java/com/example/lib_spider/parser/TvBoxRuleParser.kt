package com.example.lib_spider.parser

import com.example.lib_spider.model.RuleConfig

class TvBoxRuleParser : RuleParser {
    override fun parseRuleFile(fileContent: String): RuleConfig {
        // 简单示例，实际需解析 tvBox 规则
        return RuleConfig("tvbox", mapOf("raw" to fileContent))
    }
} 