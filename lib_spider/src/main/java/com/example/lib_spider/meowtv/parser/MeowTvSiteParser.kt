package com.example.lib_spider.meowtv.parser

import com.example.lib_database.entity.TvBoxSiteEntity
import org.json.JSONObject

object MeowTvSiteParser {
    fun parseSites(json: String): List<TvBoxSiteEntity> {
        val obj = JSONObject(json)
        val sites = obj.getJSONArray("sites")
        val result = mutableListOf<TvBoxSiteEntity>()
        for (i in 0 until sites.length()) {
            val site = sites.getJSONObject(i)
            result.add(
                TvBoxSiteEntity(
                    key = site.optString("key"),
                    name = site.optString("name"),
                    api = site.optString("api"),
                    jar = site.optString("jar"),
                    ext = site.optString("ext")
                )
            )
        }
        return result
    }
} 