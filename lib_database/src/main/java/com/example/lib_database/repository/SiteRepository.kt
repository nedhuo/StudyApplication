package com.example.lib_database.repository

import com.example.lib_database.dao.SiteDao
import com.example.lib_database.entity.SiteEntity

class SiteRepository(private val siteDao: SiteDao) {
    suspend fun insertSites(sites: List<SiteEntity>) = siteDao.insertSites(sites)
    suspend fun getAllSites() = siteDao.getAllSites()
} 