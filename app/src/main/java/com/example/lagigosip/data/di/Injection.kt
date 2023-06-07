package com.example.lagigosip.data.di

import android.content.Context
import com.example.lagigosip.data.NewsRepository
import com.example.lagigosip.data.local.room.NewsDb
import com.example.lagigosip.data.remote.retrofit.ApiConfig

object Injection {

    fun provideRepository(context: Context): NewsRepository {

        val apiService = ApiConfig.getApiService()
        val database = NewsDb.getInstance(context)
        val dao = database.newsDao()
        return NewsRepository.getInstance(apiService, dao)
    }
}