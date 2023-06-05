package com.example.lagigosip.ui.detailnews

import androidx.lifecycle.ViewModel
import com.example.lagigosip.data.NewsRepository

class DetailNewsViewModel(private val newsRepository: NewsRepository) : ViewModel() {
    fun getHeadlineNews() = newsRepository.getHeadlineNews()
}