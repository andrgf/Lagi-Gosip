package com.example.lagigosip.data

sealed class Result<out R> private constructor() {

    data class Succes<out T>(val data: T) : com.example.lagigosip.data.Result<T>()
    data class Error(val error: String) : com.example.lagigosip.data.Result<Nothing>()
    object Loading : com.example.lagigosip.data.Result<Nothing>()

}
