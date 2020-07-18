package com.mmk.quotesapp.repository

import com.mmk.quotesapp.data.ResponsePictureResult
import com.mmk.quotesapp.network.NetworkResource
import com.mmk.quotesapp.network.PhotoService
import java.io.IOException
import javax.inject.Inject

class PicturesRepository @Inject constructor(private val apiService: PhotoService) {

    suspend fun getPictureList(pageNumber:String?="1"):NetworkResource<List<ResponsePictureResult>>{
        return try {
            val response=apiService.loadPhotos(pageNumber)
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