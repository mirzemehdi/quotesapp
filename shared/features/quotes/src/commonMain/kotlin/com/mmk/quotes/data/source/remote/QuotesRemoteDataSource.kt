package com.mmk.quotes.data.source.remote

import com.mmk.core.model.Result
import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse
import kotlinx.coroutines.flow.Flow

interface QuotesRemoteDataSource {
    suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<QuoteResponse>>

    fun observeFirstPageQuotes(pageLimit: Int): Flow<Result<List<QuoteResponse>>>

    suspend fun addNewQuote(newQuoteRequest: NewQuoteRequest): Result<Unit>
}
