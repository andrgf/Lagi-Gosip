package com.example.lagigosip.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lagigosip.BuildConfig
import com.example.lagigosip.data.NewsRepository
import com.example.lagigosip.data.remote.response.ArticlesItem
import kotlinx.coroutines.launch

class SearchViewModel(private val newsRepository: NewsRepository) : ViewModel() {

}