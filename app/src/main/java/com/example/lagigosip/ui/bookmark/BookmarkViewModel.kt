package com.example.lagigosip.ui.bookmark

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lagigosip.data.NewsRepository
import com.example.lagigosip.data.local.entity.NewsEntity
import kotlinx.coroutines.launch

class BookmarkViewModel(private val newsRepository: NewsRepository) : ViewModel() {

    fun getBookmark() = newsRepository.getBookmarked()

    fun delete(news: NewsEntity) {
        viewModelScope.launch {
            newsRepository.setBookmark(news, false)
        }
    }
}