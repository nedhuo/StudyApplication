package com.example.feature_tvbox.list

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lib_database.DatabaseManager
import com.example.lib_database.repository.SiteRepository
import com.example.lib_database.dao.VideoDao

class TvBoxListViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TvBoxListViewModel::class.java)) {
            val context = application.applicationContext
            val databaseManager = DatabaseManager.getInstance(context)
            val videoDao = databaseManager.videoDao
            @Suppress("UNCHECKED_CAST")
            return TvBoxListViewModel(databaseManager, videoDao, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
} 