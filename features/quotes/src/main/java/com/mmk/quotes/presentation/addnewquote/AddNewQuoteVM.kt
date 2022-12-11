package com.mmk.quotes.presentation.addnewquote

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mmk.common.ui.SingleEvent
import com.mmk.common.ui.UiState
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.usecase.addquote.AddNewQuote
import timber.log.Timber
import androidx.lifecycle.viewModelScope
import com.mmk.common.ui.ErrorMessage
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.onError
import com.mmk.core.model.onSuccess
import kotlinx.coroutines.launch


class AddNewQuoteVM constructor(private val addNewQuoteUseCase: AddNewQuote):ViewModel()  {

    val quoteAuthor = MutableLiveData("")
    val quoteText = MutableLiveData("")

    private val _uiState: MutableLiveData<UiState> = MutableLiveData(UiState.HasData)
    val uiState: LiveData<UiState> = _uiState

    private val _onQuoteAdded = MutableLiveData<SingleEvent<Unit>>()
    val onQuoteAdded: LiveData<SingleEvent<Unit>> = _onQuoteAdded

    private val _noNetworkConnectionEvent: MutableLiveData<SingleEvent<Unit>> = MutableLiveData()
    val noNetworkConnectionEvent: LiveData<SingleEvent<Unit>> = _noNetworkConnectionEvent


    fun addQuote() = viewModelScope.launch {
        val quote = Quote(author = quoteAuthor.value ?: "", text = quoteText.value ?: "")
        _uiState.value=UiState.Loading
        addNewQuoteUseCase(quote).onSuccess {
            _onQuoteAdded.value = SingleEvent(Unit)
            _uiState.value = UiState.HasData
        }
            .onError {onErrorOccurred(it)
        }

    }

    private fun onErrorOccurred(errorEntity: ErrorEntity?) {
        val errorMessage: ErrorMessage?
        when (errorEntity) {
            ErrorEntity.NetworkConnection -> {
                _noNetworkConnectionEvent.value = SingleEvent(Unit)
                errorMessage = null
            }
            is ErrorEntity.ApiError,
            is ErrorEntity.FeatureError,
            is ErrorEntity.Unexpected,
            null
            -> errorMessage =
                ErrorMessage.ResourceId(com.mmk.common.ui.R.string.msg_unknown_error_occurred)
        }
        _uiState.value = UiState.Error(errorMessage)
    }


}