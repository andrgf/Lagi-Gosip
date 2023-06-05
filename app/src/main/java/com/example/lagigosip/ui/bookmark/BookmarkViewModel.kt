package com.example.lagigosip.ui.bookmark

import androidx.lifecycle.ViewModel
import com.example.lagigosip.data.NewsRepository
import com.example.lagigosip.data.local.entity.NewsEntity

class BookmarkViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getBookmark() = newsRepository.getBookmarked()

    fun delete(news: NewsEntity) {
        newsRepository.setBookmark(news, false)
    }
}