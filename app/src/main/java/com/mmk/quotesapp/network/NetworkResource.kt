package com.mmk.quotesapp.network

sealed class NetworkResource<T> {

    data class Success<T>(val data: T) : NetworkResource<T>()
    data class Error<T>(val message: String?, val responseCode: Int?) : NetworkResource<T>()
    data class NetworkException<T>(val message: String?) : NetworkResource<T>()

}