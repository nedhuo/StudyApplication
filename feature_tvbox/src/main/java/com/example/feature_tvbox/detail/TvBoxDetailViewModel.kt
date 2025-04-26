package com.example.feature_tvbox.detail

import androidx.lifecycle.ViewModel
import com.example.lib_database.entity.VideoEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TvBoxDetailViewModel : ViewModel() {
    private val _videoItem = MutableStateFlow<VideoEntity?>(null)
    val videoItem: StateFlow<VideoEntity?> = _videoItem

    fun setVideoItem(item: VideoEntity) {
        _videoItem.value = item
    }
} 