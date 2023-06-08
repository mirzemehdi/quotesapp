package com.mmk.quotes.data.source.remote.apiservice

import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlin.math.min

class FakeQuotesApiServiceImpl : QuotesApiService {
    val exception = IllegalArgumentException()
    var shouldThrowException: Boolean = false
    private val quoteList = mutableListOf<QuoteResponse>()
    private val newQuoteAdded = MutableSharedFlow<QuoteResponse>()

    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int,
    ): List<QuoteResponse> {
        if (shouldThrowException) throw ApiServiceException(exception)
        val sortedList = getOrderedQuoteList()
        val startIndex =
            if (pageIndex == null) 0 else (sortedList.indexOfFirst { pageIndex.toLong() == it.timeStamp } + 1)
        val endIndex = min(startIndex + pageLimit, sortedList.size)

        if (startIndex >= sortedList.size) return emptyList()
        return sortedList.subList(startIndex, endIndex)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeFirstPageQuotes(pageLimit: Int): Flow<List<QuoteResponse>> =
        newQuoteAdded.mapLatest {
            val sortedList = getOrderedQuoteList()
            sortedList.subList(0, min(pageLimit, sortedList.size))
        }.onStart { emit(emptyList()) }

    override suspend fun addNewQuote(quote: NewQuoteRequest) {
        if (shouldThrowException) throw ApiServiceException(exception)
        val quoteResponse = QuoteResponse(
            id = quote.id,
            author = quote.author,
            text = quote.text,
            timeStamp = quote.timeStamp
        )
        quoteList.add(quoteResponse)
        newQuoteAdded.emit(quoteResponse)
    }

    private fun getOrderedQuoteList(): List<QuoteResponse> {
        return quoteList.sortedByDescending { it.timeStamp }
    }
}
