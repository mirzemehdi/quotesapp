package com.mmk.quotes.data

import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onStart
import kotlin.math.min

// TODO share this between source sets
class FakeQuotesRepository : QuotesRepository {

    var shouldReturnSuccessResult: Boolean = true
    var shouldReturnEmptyList: Boolean = false
    val quoteList = mutableListOf<Quote>()
    private val newQuoteAdded = MutableSharedFlow<Quote>()

    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int,
    ): Result<List<Quote>> {
        return if (shouldReturnSuccessResult) {
            val fromIndex = 0
            val toIndex = fromIndex + pageLimit
            if (shouldReturnEmptyList) Result.success(emptyList())
            else Result.success(quoteList.subList(fromIndex, min(toIndex, quoteList.size)))
        } else Result.error()
    }

    override suspend fun addNewQuote(quote: Quote): Result<Unit> {
        return if (shouldReturnSuccessResult) {
            quoteList.add(quote)
            newQuoteAdded.emit(quote)
            Result.EMPTY
        } else Result.error()
    }

    override fun listenForDataChange(pageLimit: Int): Flow<Result<List<Quote>>> =
        newQuoteAdded.mapLatest {
            val paginatedList = quoteList.subList(0, min(pageLimit, quoteList.size))
            Result.success(paginatedList.toList())
        }.onStart { emit(Result.success(quoteList.subList(0, min(pageLimit, quoteList.size)))) }
}
