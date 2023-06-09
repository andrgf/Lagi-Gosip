package com.example.lagigosip.data

import com.example.lagigosip.data.remote.response.ArticlesItem

sealed class Result<out R> private constructor() {

    data class Success<out T>(val data: T) : com.example.lagigosip.data.Result<T>()
    data class Error(val error: String) : com.example.lagigosip.data.Result<Nothing>()
    object Loading : com.example.lagigosip.data.Result<Nothing>()

}
