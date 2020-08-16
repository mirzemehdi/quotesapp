package com.mmk.quotesapp.ui.fragments.quoteslist

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mmk.quotesapp.model.Quote
import com.mmk.quotesapp.network.NetworkResource
import com.mmk.quotesapp.repository.QuoteRepository
import com.mmk.quotesapp.utils.UiState
import com.mmk.quotesapp.utils.toast
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.withContext
import timber.log.Timber

/**
 * Created by mirzemehdi on 8/12/20
 */
class QuotesViewModel @ViewModelInject constructor(
    private val quotesRepository: QuoteRepository,
    application: Application
) : AndroidViewModel(application) {

    private val applicationContext = application.applicationContext
    val uiState=MutableLiveData<UiState>(UiState.NotLoading)
    var quotesList: LiveData<PagingData<Quote>>? =null

    init {
        getQuotes()
        Timber.e("Repository hashcode: ${quotesRepository.hashCode()}")
    }






     private fun getQuotes() {

         quotesList=quotesRepository.getQuotes().cachedIn(viewModelScope)
//        quotesList= liveData(IO){
//            val response=quotesRepository.getQuotes()
//            withContext(Main) {
//                uiState.value = UiState.NotLoading
//                when (response) {
//
//                    is NetworkResource.Success -> emit(response.data)
//                    is NetworkResource.Error -> {
//                        applicationContext.toast(response.message)
//                        Timber.e(response.message)
//                    }
//
//                    is NetworkResource.NetworkException -> Unit
//                }
//            }
//        }
    }

}