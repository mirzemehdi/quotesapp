package com.mmk.quotes.data.source.remote.apiservice

import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse
import kotlinx.coroutines.flow.Flow

interface QuotesApiService {
    suspend fun getQuotesByPagination(pageIndex: String?, pageLimit: Int): List<QuoteResponse>
    fun observeFirstPageQuotes(pageLimit: Int): Flow<List<QuoteResponse>>
    suspend fun addNewQuote(quote: NewQuoteRequest)
}
