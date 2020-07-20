package com.mmk.quotesapp.ui.fragments.main

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import com.mmk.quotesapp.model.PictureData
import com.mmk.quotesapp.repository.PicturesRepository

class MainViewHolder @ViewModelInject constructor(
    private val repository: PicturesRepository,
    application: Application
) : AndroidViewModel(application) {

    private val applicationContext = application.applicationContext
    

    fun getPictureList(): LiveData<PagingData<PictureData>> = repository.getPictureList()

}
