package com.example.lagigosip.ui.home

import androidx.lifecycle.ViewModel
import com.example.lagigosip.data.NewsRepository
import com.example.lagigosip.data.local.entity.NewsEntity

class HomeViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadlineNews()

    fun saveNews(news: NewsEntity) {
        newsRepository.setBookmark(news, true)
    }

    fun delete(news: NewsEntity) {
        newsRepository.setBookmark(news, false)
    }
}