package com.example.lib_spider.meowtv.spider

import com.example.lib_database.entity.SiteEntity
import com.example.lib_database.entity.VideoEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

class NmvodSpider : SiteSpider {
    override suspend fun fetchVideos(site: SiteEntity, keyword: String?): List<VideoEntity> = withContext(Dispatchers.IO) {
        val apiUrl = decodeExt(site.ext)
        val searchUrl = "$apiUrl/search?wd=${keyword ?: ""}"
        val conn = URL(searchUrl).openConnection() as HttpURLConnection
        conn.connectTimeout = 5000
        conn.readTimeout = 5000
        conn.requestMethod = "GET"
        val result = conn.inputStream.bufferedReader().readText()
        val json = JSONObject(result)
        val list = mutableListOf<VideoEntity>()
        val videos = json.optJSONArray("list") ?: return@withContext list
        for (i in 0 until videos.length()) {
            val v = videos.getJSONObject(i)
            list.add(
                VideoEntity(
                    url = v.optString("vod_play_url"),
                    title = v.optString("vod_name"),
                    cover = v.optString("vod_pic")
                )
            )
        }
        list
    }

    private fun decodeExt(ext: String?): String {
        return ext?.let {
            try {
                String(android.util.Base64.decode(it, android.util.Base64.DEFAULT))
            } catch (e: Exception) {
                it
            }
        } ?: ""
    }
} 