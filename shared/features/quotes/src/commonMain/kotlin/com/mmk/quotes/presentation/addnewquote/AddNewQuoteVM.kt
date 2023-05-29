package com.mmk.quotes.presentation.addnewquote

import com.mmk.common.ui.MR
import com.mmk.common.ui.util.UiMessage
import com.mmk.common.ui.util.errorhandling.UiMessageHandler
import com.mmk.common.ui.util.errorhandling.UiMessageHandlerImpl
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.onError
import com.mmk.core.model.onSuccess
import com.mmk.core.model.viewmodel.ViewModel
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.usecase.addquote.AddNewQuote
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AddNewQuoteVM constructor(private val addNewQuoteUseCase: AddNewQuote) :
    ViewModel(), UiMessageHandler by UiMessageHandlerImpl() {

    private val _quoteAuthor = MutableStateFlow("")
    val quoteAuthor: StateFlow<String> = _quoteAuthor.asStateFlow()

    private val _quoteText = MutableStateFlow("")
    val quoteText: StateFlow<String> = _quoteText.asStateFlow()

    private val _uiState = MutableStateFlow(AddNewQuoteUiState())
    val uiState: StateFlow<AddNewQuoteUiState> = _uiState.asStateFlow()

    fun addQuote() = viewModelScope.launch {
        val quote = Quote(author = quoteAuthor.value, text = quoteText.value)
        _uiState.update { it.copy(isLoading = true) }
        addNewQuoteUseCase(quote)
            .onSuccess {
                _uiState.update { it.copy(newAddedQuote = quote, isLoading = false) }
            }
            .onError {
                _uiState.update { it.copy(isLoading = false) }
                onErrorOccurred(it)
            }
    }

    fun onAuthorTextChanged(newValue: String) {
        _quoteAuthor.value = newValue
    }

    fun onQuoteTextChanged(newValue: String) {
        _quoteText.value = newValue
    }

    private suspend fun onErrorOccurred(errorEntity: ErrorEntity?) {
        val errorMessage: UiMessage = when (errorEntity) {
            ErrorEntity.NetworkConnection -> {
                UiMessage.Resource(MR.strings.msg_no_network_connection)
            }
            is ErrorEntity.ApiError,
            is ErrorEntity.FeatureError,
            is ErrorEntity.Unexpected,
            null
            -> UiMessage.Resource(MR.strings.msg_unknown_error_occurred)
        }
        emitMessage(errorMessage)
    }
}
