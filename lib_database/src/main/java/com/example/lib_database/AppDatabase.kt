package com.example.lib_database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lib_database.dao.PlayHistoryDao
import com.example.lib_database.dao.VideoSourceDao
import com.example.lib_database.entity.PlayHistory
import com.example.lib_database.entity.VideoSource

@Database(
    entities = [
        VideoSource::class,
        PlayHistory::class
    ],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun videoSourceDao(): VideoSourceDao
    abstract fun playHistoryDao(): PlayHistoryDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                .fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }
} 