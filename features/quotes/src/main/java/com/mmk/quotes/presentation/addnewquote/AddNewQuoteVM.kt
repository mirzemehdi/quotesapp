package com.mmk.quotes.presentation.addnewquote

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.common.ui.ErrorMessage
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.onError
import com.mmk.core.model.onSuccess
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.usecase.addquote.AddNewQuote
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class AddNewQuoteVM constructor(private val addNewQuoteUseCase: AddNewQuote) : ViewModel() {

    private val _quoteAuthor = MutableStateFlow("")
    val quoteAuthor: StateFlow<String> = _quoteAuthor.asStateFlow()

    private val _quoteText = MutableStateFlow("")
    val quoteText: StateFlow<String> = _quoteText.asStateFlow()

    private val _uiState = MutableStateFlow(AddNewQuoteUiState())
    val uiState: StateFlow<AddNewQuoteUiState> = _uiState.asStateFlow()

    private val _errorMessage = MutableSharedFlow<ErrorMessage?>()
    val errorMessage: SharedFlow<ErrorMessage?> = _errorMessage.asSharedFlow()
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

    private fun onErrorOccurred(errorEntity: ErrorEntity?) = viewModelScope.launch {
        val errorMessage: ErrorMessage = when (errorEntity) {
            ErrorEntity.NetworkConnection -> {
                ErrorMessage.ResourceId(com.mmk.common.ui.R.string.msg_no_network_connection)
            }
            is ErrorEntity.ApiError,
            is ErrorEntity.FeatureError,
            is ErrorEntity.Unexpected,
            null
            -> ErrorMessage.ResourceId(com.mmk.common.ui.R.string.msg_unknown_error_occurred)
        }
        _errorMessage.emit(errorMessage)
        // TODO show uiMessage
    }
}
