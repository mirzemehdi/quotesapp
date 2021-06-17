package com.mmk.domain.repository

import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result

interface QuotesRepository {
    suspend fun getQuotesByPagination(pageIndex: String?, pageLimit: Int): Result<List<Quote>>
    suspend fun addNewQuote(quote: Quote): Result<Unit>
}