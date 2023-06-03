package com.mmk.quotes.data.source.remote.apiservice

import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse

interface QuotesApiService {

    suspend fun getQuotesByPagination(pageIndex: String?, pageLimit: Int): List<QuoteResponse>
    suspend fun addNewQuote(quote: NewQuoteRequest)
}
