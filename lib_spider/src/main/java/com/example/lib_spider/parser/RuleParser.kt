package com.example.lib_spider.parser

import com.example.lib_spider.model.RuleConfig

interface RuleParser {
    fun parseRuleFile(fileContent: String): RuleConfig
} 