package com.mmk.quotes.presentation.allquotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.mmk.common.ui.util.UiMessage
import com.mmk.common.ui.util.errorhandling.UiMessageHandler
import com.mmk.common.ui.util.errorhandling.UiMessageHandlerImpl
import com.mmk.core.model.ErrorEntity
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.util.PagingException
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class QuotesVM(quotesPagingSourceFactory: () -> PagingSource<String, Quote>) :
    ViewModel(), UiMessageHandler by UiMessageHandlerImpl() {

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

    private val _getQuotesUiState: MutableStateFlow<QuotesUiState> =
        MutableStateFlow(QuotesUiState.Loading)
    val getQuotesUiState: StateFlow<QuotesUiState> = _getQuotesUiState.asStateFlow()

    val quotesListFlow: Flow<PagingData<Quote>> =
        pagingData.flow.onStart { _getQuotesUiState.value = QuotesUiState.Loading }

    fun onPageAdapterLoadingStateChanged(loadState: LoadState, totalItemCount: Int = 0) =
        viewModelScope.launch {
            when (loadState) {
                is LoadState.NotLoading ->
                    _getQuotesUiState.value =
                        if (totalItemCount < 1) QuotesUiState.Empty else QuotesUiState.HasData
                LoadState.Loading -> Unit

                is LoadState.Error -> {
                    if (loadState.error is PagingException)
                        onErrorOccurred((loadState.error as PagingException).errorEntity)
                    else
                        onErrorOccurred(ErrorEntity.unexpected(loadState.error))
                }
            }
        }

    private fun onErrorOccurred(errorEntity: ErrorEntity?) = viewModelScope.launch {
        sendMessage(
            when (errorEntity) {
                ErrorEntity.NetworkConnection -> {

                    UiMessage.ResourceId(com.mmk.common.ui.R.string.msg_no_network_connection)
                }
                is ErrorEntity.ApiError,
                is ErrorEntity.FeatureError,
                is ErrorEntity.Unexpected,
                null
                -> UiMessage.ResourceId(com.mmk.common.ui.R.string.msg_unknown_error_occurred)
            }
        )
    }
}
