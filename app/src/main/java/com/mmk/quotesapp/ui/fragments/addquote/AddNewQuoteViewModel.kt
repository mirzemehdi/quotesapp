package com.mmk.quotesapp.ui.fragments.addquote

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mmk.quotesapp.model.Quote
import com.mmk.quotesapp.network.NetworkResource
import com.mmk.quotesapp.repository.QuoteRepository
import com.mmk.quotesapp.utils.UiState
import com.mmk.quotesapp.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddNewQuoteViewModel @ViewModelInject constructor(
    private val quotesRepository: QuoteRepository,
    application: Application
) : AndroidViewModel(application) {

    private val applicationContext = application.applicationContext
    val quoteAuthor = MutableLiveData<String>()
    val quoteText = MutableLiveData<String>()
    private val _uiState=MutableLiveData<UiState>(UiState.NotLoading)
    val uiState:LiveData<UiState> = _uiState
    private val _onQuoteAdded=MutableLiveData(false)
    val onQuoteAdded=_onQuoteAdded



    fun addQuote() {
        _uiState.value=UiState.Loading
        val quote = Quote(quoteAuthor.value!!, quoteText.value!!)
        CoroutineScope(IO).launch {
            val response = quotesRepository.addNewQuote(quote)
            withContext(Main) {
                _uiState.value=UiState.NotLoading
                when (response) {

                    is NetworkResource.Success -> {
                        applicationContext.toast("AddedQuote ID: ${response.data}")
                        _onQuoteAdded.value=true
                    }
                    is NetworkResource.Error -> applicationContext.toast(response.message)
                    is NetworkResource.NetworkException -> Unit
                }
            }
        }

    }

}