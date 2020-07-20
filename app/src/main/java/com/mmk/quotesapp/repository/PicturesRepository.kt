package com.mmk.quotesapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.liveData
import com.mmk.quotesapp.model.PictureData
import com.mmk.quotesapp.di.PicturePager
import javax.inject.Inject

class PicturesRepository @Inject constructor(@PicturePager.PagerPicture private val pager: Pager<Int, PictureData>) {


    fun getPictureList(): LiveData<PagingData<PictureData>> {
        return pager.liveData
    }

}