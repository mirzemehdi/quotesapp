package com.mmk.quotesapp.repository

import com.mmk.quotesapp.data.ResponseQuote
import com.mmk.quotesapp.network.NetworkResource
import com.mmk.quotesapp.network.QuotesService
import java.io.IOException
import javax.inject.Inject

class QuotesRepository @Inject constructor(private val apiService: QuotesService) {

    suspend fun getRandomQuote():NetworkResource<ResponseQuote>{
        return try {
            val response=apiService.getRandomQuote()
            if (response.isSuccessful && response.body()!=null){
                NetworkResource.Success(response.body()!!)
            }
            else
                NetworkResource.Error(response.message(),response.code())
        }catch (e:IOException){
            NetworkResource.NetworkException(e.message)
        }
    }
}