package com.mmk.quotes.data.source.remote

import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse

interface QuotesRemoteDataSource {
    suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<QuoteResponse>>

    suspend fun addNewQuote(newQuoteRequest: NewQuoteRequest): Result<Unit>
}
