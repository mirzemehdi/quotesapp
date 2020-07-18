package com.mmk.quotesapp.ui.fragments.main

import android.app.Application
import android.widget.Toast
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mmk.quotesapp.data.PictureData
import com.mmk.quotesapp.network.NetworkResource
import com.mmk.quotesapp.repository.PicturesRepository
import com.mmk.quotesapp.utils.toPictureDataList
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext

class MainViewHolder @ViewModelInject constructor(
    private val repository: PicturesRepository,
    application: Application
) : AndroidViewModel(application) {

    private val applicationContext = application.applicationContext

    var pictureListResponse: LiveData<List<PictureData>>? = null


    fun getPictureList() {
        pictureListResponse = liveData(IO) {
            val response = repository.getPictureList()
            when (response) {

                is NetworkResource.Success ->   emit(response.data.toPictureDataList())
                is NetworkResource.Error ->  withContext(Main) {
                    Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT).show()
                }

                is NetworkResource.NetworkException -> withContext(Main) {
                    Toast.makeText(applicationContext, response.message, Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }
    }
}
