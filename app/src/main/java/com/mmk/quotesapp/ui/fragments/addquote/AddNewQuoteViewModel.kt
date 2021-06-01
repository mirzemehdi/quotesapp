package com.mmk.quotesapp.ui.fragments.addquote

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mmk.domain.interaction.quote.addquote.AddNewQuoteUseCase
import com.mmk.domain.model.Quote
import com.mmk.domain.model.onError
import com.mmk.domain.model.onSuccess
import com.mmk.quotesapp.ui.base.BaseViewModel

import com.mmk.quotesapp.ui.base.UiState
import com.mmk.quotesapp.utils.SingleEvent
import com.mmk.quotesapp.utils.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

class AddNewQuoteViewModel constructor(
    private val addNewQuoteUseCase: AddNewQuoteUseCase

) : BaseViewModel() {

    val quoteAuthor = MutableLiveData("")
    val quoteText = MutableLiveData("")

    private val _onQuoteAdded = MutableLiveData<SingleEvent<Boolean>>()
    val onQuoteAdded: LiveData<SingleEvent<Boolean>> = _onQuoteAdded


    fun addQuote() {
        executeUseCase {
            val quote = Quote(author = quoteAuthor.value ?: "", text = quoteText.value ?: "")
            addNewQuoteUseCase(quote).onSuccess {
                _onQuoteAdded.value = SingleEvent(true)
                _uiState.value = UiState.HasData
            }.onError { message, errorCode ->
                Timber.e(message)
            }

        }


    }

}