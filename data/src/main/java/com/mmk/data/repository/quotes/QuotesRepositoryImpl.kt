package com.mmk.data.repository.quotes

import com.mmk.data.remote.quotes.QuotesRemoteDataSource
import com.mmk.data.remote.quotes.model.response.QuoteResponse
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.model.onSuccess
import com.mmk.domain.repository.QuotesRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class QuotesRepositoryImpl(
    private val remoteDataSource: QuotesRemoteDataSource,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) : QuotesRepository {

    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<Quote>> {
        val quoteListResponse: Result<List<QuoteResponse>>
        withContext(ioDispatcher) {
            quoteListResponse = remoteDataSource.getQuotesByPagination(pageIndex, pageLimit)
        }
        quoteListResponse.onSuccess { list ->
            return Result.Success(list.map { it.mapToDomainModel() })
        }
        return quoteListResponse as Result.Error
    }


    override suspend fun addNewQuote(quote: Quote): Result<Unit> {
        return withContext(ioDispatcher) {
            remoteDataSource.addNewQuote(quote)
        }
    }
}