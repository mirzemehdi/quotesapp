package com.mmk.quotes.presentation.allquotes

import com.mmk.quotes.domain.model.Quote

sealed interface QuotesUiState {
    data class HasData(
        val quotesList: List<Quote> = emptyList(),
        val currentPage: String? = null,
        val hasReachedEnd: Boolean = false,
        val isPaginationLoading: Boolean = false,
        val hasPaginationError: Boolean = false
    ) : QuotesUiState

    object Loading : QuotesUiState
    object Empty : QuotesUiState
}
