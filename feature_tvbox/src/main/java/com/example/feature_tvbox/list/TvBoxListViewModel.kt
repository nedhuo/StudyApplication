package com.example.feature_tvbox.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_database.entity.VideoEntity
import com.example.lib_database.repository.SiteRepository
import com.example.lib_database.dao.VideoDao
import com.example.lib_spider.meowtv.spider.MeowTvSiteSpider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TvBoxListViewModel(
    private val siteRepository: SiteRepository,
    private val videoDao: VideoDao
) : ViewModel() {
    private val _videoList = MutableStateFlow<List<VideoEntity>>(emptyList())
    val videoList: StateFlow<List<VideoEntity>> = _videoList

    fun loadAllVideos(keyword: String? = null) {
        viewModelScope.launch {
            val sites = siteRepository.getAllSites()
            for (site in sites) {
                MeowTvSiteSpider.fetchAndSaveVideos(site, videoDao, keyword)
            }
            // 加载所有视频
            _videoList.value = videoDao.getAllVideos()
        }
    }
} 