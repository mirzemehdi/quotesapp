package com.mmk.quotes.domain.repository

import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote

interface QuotesRepository {
    suspend fun getQuotesByPagination(pageIndex: String?, pageLimit: Int): Result<List<Quote>>
    suspend fun addNewQuote(quote: Quote): Result<Unit>
}
