package com.example.lagigosip.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.lagigosip.data.local.entity.NewsEntity

@Database(entities = [NewsEntity::class], version = 1)
abstract class NewsDb : RoomDatabase() {
    abstract fun newsDao(): NewsDao

    companion object {
        @Volatile
        private var instance: NewsDb? = null
        fun getInstance(context: Context): NewsDb =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    NewsDb::class.java, "News.db"
                ).build()
            }
    }
}