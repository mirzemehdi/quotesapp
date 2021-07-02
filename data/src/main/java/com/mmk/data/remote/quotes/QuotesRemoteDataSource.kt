package com.mmk.data.remote.quotes

import com.mmk.data.remote.quotes.model.response.QuoteResponse
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result

interface QuotesRemoteDataSource {

    suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<QuoteResponse>>

    suspend fun addNewQuote(quote: Quote): Result<Unit>
}