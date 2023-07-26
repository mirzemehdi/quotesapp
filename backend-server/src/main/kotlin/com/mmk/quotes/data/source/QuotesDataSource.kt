package com.mmk.quotes.data.source

import com.mmk.quotes.data.model.Quote
import kotlinx.coroutines.flow.Flow

interface QuotesDataSource {

    suspend fun getQuotesByPagination(pageIndex: String? = null, pageLimit: Int = 20): List<Quote>
    suspend fun insertQuote(quote: Quote)

    fun listenForFirstPageDataChanges(pageLimit: Int): Flow<List<Quote>>

}