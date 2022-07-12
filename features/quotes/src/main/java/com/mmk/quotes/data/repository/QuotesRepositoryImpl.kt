package com.mmk.quotes.data.repository

import com.mmk.core.model.Result
import com.mmk.quotes.data.source.remote.QuotesRemoteDataSource
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.repository.QuotesRepository

class QuotesRepositoryImpl(private val quotesRemoteDataSource: QuotesRemoteDataSource) :
    QuotesRepository {
    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<Quote>> {

        return when (
            val result =
                quotesRemoteDataSource.getQuotesByPagination(pageIndex, pageLimit)
        ) {
            is Result.Error -> result
            is Result.Success -> Result.success(result.data.map { it.mapToDomainModel() })
        }
    }

    override suspend fun addNewQuote(quote: Quote): Result<Unit> {
        TODO("Not yet implemented")
    }
}
