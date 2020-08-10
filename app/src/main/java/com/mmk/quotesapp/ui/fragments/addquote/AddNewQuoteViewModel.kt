package com.mmk.quotesapp.ui.fragments.addquote

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.mmk.quotesapp.model.Quote
import com.mmk.quotesapp.network.NetworkResource
import com.mmk.quotesapp.repository.QuoteRepository
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


    fun addQuote() {
        val quote = Quote(quoteAuthor.value!!, quoteText.value!!)
        CoroutineScope(IO).launch {
            val response = quotesRepository.addNewQuote(quote)
            withContext(Main) {
                when (response) {

                    is NetworkResource.Success -> applicationContext.toast("AddedQuote ID: ${response.data}")
                    is NetworkResource.Error -> applicationContext.toast(response.message)
                    is NetworkResource.NetworkException -> Unit
                }
            }
        }

    }

}