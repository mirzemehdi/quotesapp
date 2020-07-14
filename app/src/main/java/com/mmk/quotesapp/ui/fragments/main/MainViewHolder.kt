package com.mmk.quotesapp.ui.fragments.main

import android.app.Application
import android.widget.Toast
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.mmk.quotesapp.data.ResponseQuote
import com.mmk.quotesapp.network.NetworkResource
import com.mmk.quotesapp.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext

class MainViewHolder @ViewModelInject constructor(
    private val repository: QuotesRepository,
    application: Application
) : AndroidViewModel(application) {

    private val applicationContext = application.applicationContext

    var randomQuoteResponse: LiveData<ResponseQuote>? = null


    fun getRandomQuote() {
        randomQuoteResponse = liveData(IO) {
            val response = repository.getRandomQuote()
            when (response) {

                is NetworkResource.Success ->   emit(response.data)
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
