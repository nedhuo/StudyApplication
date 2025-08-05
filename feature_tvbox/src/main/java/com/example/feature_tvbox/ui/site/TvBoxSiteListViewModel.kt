package com.example.feature_tvbox.ui.site

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lib_database.DatabaseManager
import com.example.lib_database.entity.TvBoxSiteEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TvBoxSiteListViewModel(
    private val databaseManager: DatabaseManager
) : ViewModel() {
    private val _siteList = MutableStateFlow<List<TvBoxSiteEntity>>(emptyList())
    val siteList: StateFlow<List<TvBoxSiteEntity>> = _siteList

    fun loadSites() {
        viewModelScope.launch {
            _siteList.value = databaseManager.tvBoxSiteDao.getAllSites()
        }
    }
}