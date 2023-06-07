package com.example.lagigosip.data.remote.retrofit

import com.example.lagigosip.data.remote.response.NewsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("top-headlines?country=id&category=science")
    fun getNews(
        @Query("apiKey") apikey: String
    ): NewsResponse

    @GET("everything")
    fun getSearchNews(
        @Query("q") query: String,
        @Query("apiKey") apikey: String
    ): NewsResponse
}