package com.example.feature_tvbox.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.feature_tvbox.data.SiteSyncRepository
import com.example.lib_database.DatabaseManager
import com.example.lib_database.dao.VideoDao
import com.example.lib_database.entity.VideoEntity
import com.example.lib_spider.meowtv.spider.MeowTvSpider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TvBoxListViewModel(
    private val databaseManager: DatabaseManager,
    private val videoDao: VideoDao,
    application: Application
) : AndroidViewModel(application) {
    private val _videoList = MutableStateFlow<List<VideoEntity>>(emptyList())
    val videoList: StateFlow<List<VideoEntity>> = _videoList

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    fun fetchAndSaveMeowTvSites(onFinish: () -> Unit) {
        viewModelScope.launch {
            // 1. 拉取 JSON
            val json = MeowTvSpider.fetchMeowTvSitesJson("http://www.meowtv.top")
            // 2. 存入数据库
            DatabaseManager.getInstance(getApplication()).importFromJson(json)
            onFinish()
        }
    }

    // 集成第一步：同步站点
    fun syncSitesAndFetchVideos(siteSyncRepository: SiteSyncRepository) {
        viewModelScope.launch {
            _loading.value = true
            // 1. 拉取并存储站点
            siteSyncRepository.syncSitesFromRemote()
            // 2. 动态抓取所有站点影视数据
            val sites = databaseManager.tvBoxSiteDao.getAllSites()
//            for (site in sites) {
//                val spider = MeowTvSiteSpider()
//                val videos = spider.fetchVideos(site, null)
//                videos.forEach { videoDao.insertVideo(it) }
//            }
            // 3. 加载所有视频到 StateFlow
            _videoList.value = videoDao.getAllVideos()
            _loading.value = false
        }
    }
} 