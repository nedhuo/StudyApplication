package com.example.lib_database

import android.content.Context
import com.example.lib_database.dao.TvBoxSiteDao
import com.example.lib_database.dao.VideoDao
import com.example.lib_database.entity.PlayHistory
import com.example.lib_database.entity.VideoSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.util.UUID

class DatabaseManager private constructor(context: Context) {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private val database = AppDatabase.getInstance(context)
    private val videoSourceDao = database.videoSourceDao()
    private val playHistoryDao = database.playHistoryDao()
    val siteDao: TvBoxSiteDao get() = database.tvBoxSiteDao()
    val videoDao: VideoDao get() = database.videoDao()
    val tvBoxSiteDao: TvBoxSiteDao get() = database.tvBoxSiteDao()

    companion object {
        @Volatile
        private var instance: DatabaseManager? = null

        @JvmStatic
        fun getInstance(context: Context): DatabaseManager {
            return instance ?: synchronized(this) {
                instance ?: DatabaseManager(context).also { instance = it }
            }
        }
    }


} 