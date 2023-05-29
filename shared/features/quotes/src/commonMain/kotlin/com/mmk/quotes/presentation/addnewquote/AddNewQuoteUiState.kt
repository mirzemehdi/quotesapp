package com.mmk.quotes.presentation.addnewquote

import com.mmk.quotes.domain.model.Quote

data class AddNewQuoteUiState(val isLoading: Boolean = false, val newAddedQuote: Quote? = null)
