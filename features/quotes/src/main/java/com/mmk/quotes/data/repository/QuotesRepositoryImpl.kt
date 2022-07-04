package com.mmk.quotes.data.repository

import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.repository.QuotesRepository

class QuotesRepositoryImpl : QuotesRepository {
    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<Quote>> {
        TODO("Not yet implemented")
    }

    override suspend fun addNewQuote(quote: Quote): Result<Unit> {
        TODO("Not yet implemented")
    }
}
