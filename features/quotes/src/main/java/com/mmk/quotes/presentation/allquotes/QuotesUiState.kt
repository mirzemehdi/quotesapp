package com.mmk.quotes.presentation.allquotes

// data class QuotesUiState(val quotesPagingData: PagingData<Quote>, val isLoading: Boolean)

sealed interface QuotesUiState {
    object HasData : QuotesUiState
    object Loading : QuotesUiState
    object Empty : QuotesUiState
}
