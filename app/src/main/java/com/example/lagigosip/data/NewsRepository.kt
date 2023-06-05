package com.example.lagigosip.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.example.lagigosip.BuildConfig
import com.example.lagigosip.data.local.entity.NewsEntity
import com.example.lagigosip.data.local.room.NewsDao
import com.example.lagigosip.data.remote.response.NewsResponse
import com.example.lagigosip.data.remote.retrofit.ApiService
import com.example.lagigosip.utils.AppExecutors
import com.google.android.material.progressindicator.LinearProgressIndicatorSpec
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsRepository private constructor(
    private val apiService: ApiService,
    private val newDao: NewsDao,
    private val appExecutors: AppExecutors
){
    private val result = MediatorLiveData<com.example.lagigosip.data.Result<List<NewsEntity>>>()

    fun getHeadlineNews() : LiveData<com.example.lagigosip.data.Result<List<NewsEntity>>> {
        result.value = Result.Loading
        val client = apiService.getNews(BuildConfig.API_KEY)
        client.enqueue(object : Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                if (response.isSuccessful) {
                    val article = response.body()?.articles
                    val newsList = ArrayList<NewsEntity>()
                    appExecutors.diskIO.execute{
                        article?.forEach { article ->
                            val isSave = newDao.isNewsBookmarked(article.title)
                            val news = NewsEntity(
                                article.title,
                                article.publishedAt,
                                article.urlToImage,
                                article.url,
                                isSave,
                            )
                            newsList.add(news)
                        }
                        newDao.deleteAll()
                        newDao.insertNews(newsList)
                    }
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                result.value = com.example.lagigosip.data.Result.Error(t.message.toString())
            }

        })
        val localData = newDao.getNews()
        result.addSource(localData) {newData: List<NewsEntity> ->
            result.value = com.example.lagigosip.data.Result.Succes(newData)
        }
        return result
    }

    fun getBookmarked() : LiveData<List<NewsEntity>> {
        return newDao.getBookmarkedNews()
    }

    fun setBookmark(news: NewsEntity, state: Boolean) {
        appExecutors.diskIO.execute {
            news.isBookmarked = state
            newDao.updateNews(news)
        }
    }

    companion object {
        @Volatile
        private var instance: NewsRepository? = null
        fun getInstance(
            apiService: ApiService,
            newDao: NewsDao,
            appExecutors: AppExecutors
        ): NewsRepository =
            instance ?: synchronized(this) {
                instance ?: NewsRepository(apiService, newDao, appExecutors)
            }.also { instance = it }
    }
}