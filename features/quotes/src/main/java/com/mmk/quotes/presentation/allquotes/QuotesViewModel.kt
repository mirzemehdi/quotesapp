package com.mmk.quotes.presentation.allquotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.common.ui.ErrorMessage
import com.mmk.common.ui.SingleEvent
import com.mmk.common.ui.UiState
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.onError
import com.mmk.core.model.onSuccess
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPagination
import kotlinx.coroutines.launch

class QuotesViewModel(private val getAllQuotesByPagination: GetAllQuotesByPagination) :
    ViewModel() {

    private val _getQuotesUiState: MutableLiveData<UiState> = MutableLiveData(UiState.Loading)
    val getQuotesUiState: LiveData<UiState> = _getQuotesUiState

    private var _quotesList: MutableLiveData<List<Quote>> = MutableLiveData(emptyList())
    val quotesList: LiveData<List<Quote>> = _quotesList

    private val _noNetworkConnectionEvent: MutableLiveData<SingleEvent<Unit>> = MutableLiveData()
    val noNetworkConnectionEvent: LiveData<SingleEvent<Unit>> = _noNetworkConnectionEvent

    init {
        getQuotesByPagination()
    }

    private fun getQuotesByPagination() = viewModelScope.launch {
        getAllQuotesByPagination.invoke(null, Constants.NB_QUOTES_LIMIT_PER_PAGE)
            .onSuccess { list ->
                _quotesList.value = list
                if (list.isEmpty()) _getQuotesUiState.value = UiState.NoData
                else _getQuotesUiState.value = UiState.HasData
            }
            .onError { errorEntity ->
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
                _getQuotesUiState.value = UiState.Error(errorMessage)
            }
    }
}
