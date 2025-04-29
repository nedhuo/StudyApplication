package com.example.lib_spider.meowtv.parser

import com.example.lib_database.entity.TvBoxSiteEntity
import org.json.JSONObject

/**
 * http://ztha.top/TVBox/thdjk.json
 */
class ZthaTvSiteParser {
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
                    ext = site.optString("ext"),
                    timeout = site.optInt("timeout"),
                    playerType = site.optInt("playerType"),
                    changeable = site.optInt("changeable"),
                    filterable = site.optInt("filterable"),
                    quickSearch = site.optInt("quickSearch"),
                    searchable = site.optInt("searchable"),
                    indexs = site.optInt("indexs"),
                    type = site.optInt("type"),
                )
            )
        }
        return result
    }
}