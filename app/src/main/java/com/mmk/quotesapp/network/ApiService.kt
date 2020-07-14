package com.mmk.quotesapp.network

import com.mmk.quotesapp.data.ResponseQuote
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

interface QuotesService{

    @GET("qod/")
    suspend fun getRandomQuote():Response<ResponseQuote>
}