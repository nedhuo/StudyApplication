package com.example.lib_database.repository

import com.example.lib_database.dao.TvBoxSiteDao
import com.example.lib_database.entity.TvBoxSiteEntity

class SiteRepository(private val siteDao: TvBoxSiteDao) {
    suspend fun insertSites(sites: List<TvBoxSiteEntity>) = siteDao.insertSites(sites)
    suspend fun getAllSites() = siteDao.getAllSites()
} 