package com.mmk.quotes.presentation.allquotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mmk.common.ui.MR
import com.mmk.common.ui.util.UiMessage
import com.mmk.common.ui.util.errorhandling.UiMessageHandler
import com.mmk.common.ui.util.errorhandling.UiMessageHandlerImpl
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.onError
import com.mmk.core.model.onSuccess
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPagination
import com.mmk.quotes.presentation.allquotes.Constants.INITIAL_QUOTES_PAGE_INDEX
import com.mmk.quotes.presentation.allquotes.Constants.NB_INITIAL_QUOTES_SIZE
import com.mmk.quotes.presentation.allquotes.Constants.NB_QUOTES_LIMIT_PER_PAGE
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class QuotesVM(private val getAllQuotesByPagination: GetAllQuotesByPagination) :
    ViewModel(),
    UiMessageHandler by UiMessageHandlerImpl() {

    private val _getQuotesUiState: MutableStateFlow<QuotesUiState> =
        MutableStateFlow(QuotesUiState.Loading)
    val getQuotesUiState: StateFlow<QuotesUiState> = _getQuotesUiState.asStateFlow()

    init {
        loadQuotes()
    }

    fun loadQuotes() = viewModelScope.launch {
        onLoading()

        val (pageIndex, pageLimit) = getPageParams()

        getAllQuotesByPagination(
            pageIndex = pageIndex,
            pageLimit = pageLimit
        ).onSuccess { newQuoteList ->
            onSuccess(newQuoteList)
        }.onError {
            _getQuotesUiState.update { uiState ->
                when (uiState) {
                    is QuotesUiState.HasData -> {
                        uiState.copy(hasPaginationError = true, isPaginationLoading = false)
                    }
                    else -> {
                        QuotesUiState.Empty
                    }
                }
            }
            onErrorOccurred(it)
        }
    }

    private fun onSuccess(newQuoteList: List<Quote>) {
        val nextPage = if (newQuoteList.isEmpty()) null else newQuoteList.last().id
        _getQuotesUiState.update {
            val updatedUiStateWithList = when (it) {
                is QuotesUiState.HasData -> it.copy(quotesList = it.quotesList + newQuoteList)
                else -> QuotesUiState.HasData(quotesList = newQuoteList)
            }
            updatedUiStateWithList.copy(
                hasReachedEnd = newQuoteList.isEmpty(),
                isPaginationLoading = false,
                currentPage = nextPage,
                hasPaginationError = false
            )
        }
    }

    private fun onLoading() {
        _getQuotesUiState.update {
            when (it) {
                is QuotesUiState.HasData -> it.copy(
                    isPaginationLoading = true,
                    hasPaginationError = false
                )
                else -> QuotesUiState.Loading
            }
        }
    }

    // Returns current page index and page limit respectively
    private fun getPageParams(): Pair<String?, Int> {

        val quotesHasDataUiState: QuotesUiState.HasData? = _getQuotesUiState.value.let {
            when (it) {
                is QuotesUiState.HasData -> it
                else -> null
            }
        }
        val pageLimit =
            quotesHasDataUiState?.let { NB_QUOTES_LIMIT_PER_PAGE } ?: NB_INITIAL_QUOTES_SIZE
        val pageIndex = quotesHasDataUiState?.currentPage ?: INITIAL_QUOTES_PAGE_INDEX

        return Pair(pageIndex, pageLimit)
    }

    private fun onErrorOccurred(errorEntity: ErrorEntity?) = viewModelScope.launch {
        emitMessage(
            when (errorEntity) {
                ErrorEntity.NetworkConnection -> {

                    UiMessage.Resource(MR.strings.msg_no_network_connection)
                }
                is ErrorEntity.ApiError,
                is ErrorEntity.FeatureError,
                is ErrorEntity.Unexpected,
                null
                -> UiMessage.Resource(MR.strings.msg_unknown_error_occurred)
            }
        )
    }
}
