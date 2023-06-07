package com.example.lagigosip.data

import android.util.Log
import androidx.lifecycle.*
import com.example.lagigosip.BuildConfig
import com.example.lagigosip.data.Result
import com.example.lagigosip.data.local.entity.NewsEntity
import com.example.lagigosip.data.local.room.NewsDao
import com.example.lagigosip.data.remote.response.ArticlesItem
import com.example.lagigosip.data.remote.retrofit.ApiService

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newDao: NewsDao,
){

    fun getHeadlineNews() : LiveData<com.example.lagigosip.data.Result<List<NewsEntity>>> = liveData {
        emit(com.example.lagigosip.data.Result.Loading)
        try {
            val response = apiService.getNews(BuildConfig.API_KEY)
            val articles = response.articles
            val newsList = articles.map { article ->
                val isBookmarked = newDao.isNewsBookmarked(article.title)
                NewsEntity(
                    article.title,
                    article.publishedAt,
                    article.urlToImage,
                    article.url,
                    isBookmarked,
                )
            }
            newDao.deleteAll()
            newDao.insertNews(newsList)
        } catch (e: java.lang.Exception) {
            Log.d("NewsRepository", "getNews: ${e.message.toString()}")
            emit(com.example.lagigosip.data.Result.Error(e.message.toString()))
        }
        val localData: LiveData<com.example.lagigosip.data.Result<List<NewsEntity>>> = newDao.getNews().map { com.example.lagigosip.data.Result.Succes(it) }
        emitSource(localData)
    }

    fun getBookmarked() : LiveData<List<NewsEntity>> {
        return newDao.getBookmarkedNews()
    }

    suspend fun setBookmark(news: NewsEntity, state: Boolean) {
        news.isBookmarked = state
        newDao.updateNews(news)
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newDao: NewsDao,
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newDao)
            }.also { instance = it }
    }
}