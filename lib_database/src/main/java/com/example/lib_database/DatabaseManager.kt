package com.example.lib_database

import android.content.Context
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

    companion object {
        @Volatile
        private var instance: DatabaseManager? = null

        fun getInstance(context: Context): DatabaseManager {
            return instance ?: synchronized(this) {
                instance ?: DatabaseManager(context).also { instance = it }
            }
        }
    }

    // 视频源相关操作
    fun getAllVideoSources(): Flow<List<VideoSource>> = videoSourceDao.getAllSources()
    
    fun getVideoSourcesByType(type: Int): Flow<List<VideoSource>> = videoSourceDao.getSourcesByType(type)
    
    suspend fun addVideoSource(source: VideoSource) {
        videoSourceDao.insertSource(source)
    }
    
    suspend fun addVideoSources(sources: List<VideoSource>) {
        videoSourceDao.insertSources(sources)
    }
    
    suspend fun removeVideoSource(source: VideoSource) {
        videoSourceDao.deleteSource(source)
    }
    
    suspend fun clearVideoSources() {
        videoSourceDao.deleteAllSources()
    }

    // 播放历史相关操作
    fun getAllPlayHistory(): Flow<List<PlayHistory>> = playHistoryDao.getAllHistory()
    
    fun getPlayHistoryBySource(sourceId: String): Flow<List<PlayHistory>> = 
        playHistoryDao.getHistoryBySource(sourceId)
    
    suspend fun addPlayHistory(history: PlayHistory) {
        playHistoryDao.insertHistory(history)
    }
    
    suspend fun removePlayHistory(history: PlayHistory) {
        playHistoryDao.deleteHistory(history)
    }
    
    suspend fun clearPlayHistory() {
        playHistoryDao.clearHistory()
    }

    // 资源配置文件导入
    fun importFromJson(jsonString: String) {
        scope.launch {
            try {
                val jsonObject = JSONObject(jsonString)
                
                // 导入视频源
                val sites = jsonObject.optJSONArray("sites")
                sites?.let {
                    val sourceList = mutableListOf<VideoSource>()
                    for (i in 0 until it.length()) {
                        val site = it.getJSONObject(i)
                        sourceList.add(
                            VideoSource(
                                id = site.getString("key"),
                                name = site.getString("name"),
                                type = site.getInt("type"),
                                api = site.getString("api"),
                                searchable = site.optInt("searchable", 1),
                                quickSearch = site.optInt("quickSearch", 1),
                                filterable = site.optInt("filterable", 1),
                                ext = site.optString("ext"),
                                jar = site.optString("jar"),
                                playerType = site.optInt("playerType", 0),
                                categories = site.optString("categories"),
                                clickSelector = site.optString("click"),
                                timeout = site.optInt("timeout", 15000)
                            )
                        )
                    }
                    addVideoSources(sourceList)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    // 创建播放历史记录
    fun createPlayHistory(
        title: String,
        sourceId: String,
        episodeIndex: Int = 0,
        playPosition: Long = 0L,
        duration: Long = 0L
    ) {
        scope.launch {
            val history = PlayHistory(
                id = UUID.randomUUID().toString(),
                title = title,
                sourceId = sourceId,
                episodeIndex = episodeIndex,
                playPosition = playPosition,
                duration = duration
            )
            addPlayHistory(history)
        }
    }

    // 更新播放进度
    fun updatePlayProgress(historyId: String, position: Long, duration: Long) {
        scope.launch {
            try {
                val histories = getAllPlayHistory().first()
                val history = histories.find { it.id == historyId }
                history?.let {
                    val updatedHistory = it.copy(
                        playPosition = position,
                        duration = duration,
                        updateTime = System.currentTimeMillis()
                    )
                    addPlayHistory(updatedHistory)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
} 