package com.mmk.quotes.presentation.allquotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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

    private val _quotesList: MutableLiveData<List<Quote>> = MutableLiveData(emptyList())
    val quotesList: LiveData<List<Quote>> = _quotesList

    private val _noNetworkConnectionEvent: MutableLiveData<SingleEvent<Unit>> = MutableLiveData()
    val noNetworkConnectionEvent: LiveData<SingleEvent<Unit>> = _noNetworkConnectionEvent

    init {
        getQuotesByPagination()
    }

    @Suppress("MagicNumber")
    private fun getQuotesByPagination() = viewModelScope.launch {
        getAllQuotesByPagination.invoke(null, 10)
            .onSuccess { list ->
                _quotesList.value = list
                if (list.isEmpty()) _getQuotesUiState.value = UiState.NoData
                else _getQuotesUiState.value = UiState.HasData
            }
            .onError { errorEntity ->
                _getQuotesUiState.value = UiState.Error()
                when (errorEntity) {
                    is ErrorEntity.ApiError -> TODO()
                    is ErrorEntity.FeatureError -> TODO()
                    ErrorEntity.NetworkConnection ->
                        _noNetworkConnectionEvent.value = SingleEvent(Unit)
                    is ErrorEntity.Unexpected -> TODO()
                    null -> TODO()
                }
            }
    }
}
