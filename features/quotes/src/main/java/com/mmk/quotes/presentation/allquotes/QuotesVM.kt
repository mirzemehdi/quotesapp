package com.mmk.quotes.presentation.allquotes

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.mmk.common.ui.ErrorMessage
import com.mmk.common.ui.SingleEvent
import com.mmk.common.ui.UiState
import com.mmk.core.model.ErrorEntity
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.util.PagingException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch

class QuotesVM(private val quotesPagingSourceFactory: () -> PagingSource<String, Quote>) :
    ViewModel() {

    private val config =
        PagingConfig(
            pageSize = Constants.NB_QUOTES_LIMIT_PER_PAGE,
            initialLoadSize = Constants.NB_INITIAL_QUOTES_SIZE,
            enablePlaceholders = false
        )

    private val pagingData = Pager(
        config = config,
        pagingSourceFactory = quotesPagingSourceFactory
    )

    private val _getQuotesUiState: MutableLiveData<UiState> = MutableLiveData(UiState.Loading)
    val getQuotesUiState: LiveData<UiState> = _getQuotesUiState

    val quotesList: LiveData<PagingData<Quote>>
        get() = pagingData.flow.asLiveData()

    val quotesListFlow: Flow<PagingData<Quote>> =
        pagingData.flow.onStart { _getQuotesUiState.value = UiState.Loading }

    private val _noNetworkConnectionEvent: MutableLiveData<SingleEvent<Unit>> = MutableLiveData()
    val noNetworkConnectionEvent: LiveData<SingleEvent<Unit>> = _noNetworkConnectionEvent

    fun onPageAdapterLoadingStateChanged(loadState: LoadState, totalItemCount: Int = 0) =
        viewModelScope.launch {
            when (loadState) {
                is LoadState.NotLoading ->
                    _getQuotesUiState.value =
                        if (totalItemCount < 1) UiState.NoData else UiState.HasData
                LoadState.Loading -> Unit

                is LoadState.Error -> {
                    if (loadState.error is PagingException)
                        onErrorOccurred((loadState.error as PagingException).errorEntity)
                    else
                        onErrorOccurred(ErrorEntity.unexpected(loadState.error))
                }
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
        _getQuotesUiState.value = UiState.Error(errorMessage)
    }
}
