package com.example.lib_database.repository

import com.example.lib_database.dao.VideoDao
import com.example.lib_database.entity.VideoEntity

class VideoRepository(private val videoDao: VideoDao) {
    suspend fun insertVideo(video: VideoEntity) = videoDao.insertVideo(video)
    suspend fun getVideoByUrl(url: String) = videoDao.getVideoByUrl(url)
    suspend fun getAllVideos() = videoDao.getAllVideos()
    suspend fun deleteVideo(video: VideoEntity) = videoDao.deleteVideo(video)
} 