package com.mmk.quotesapp.network

import com.mmk.quotesapp.data.ResponsePictureResult
import com.mmk.quotesapp.utils.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface PhotoService{


    @Headers("Authorization: Client-ID $API_KEY")
    @GET("photos")
    suspend fun loadPhotos(@Query("page") pageNumber: String?): Response<List<ResponsePictureResult>?>
}