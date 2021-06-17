package com.mmk.data.repository.quotes

import com.mmk.data.remote.RemoteDataSource
import com.mmk.data.remote.model.response.QuoteResponse
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.model.onSuccess
import com.mmk.domain.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class QuotesRepositoryImpl(private val remoteDataSource: RemoteDataSource) : QuotesRepository {

    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<Quote>> {
        val quoteListResponse: Result<List<QuoteResponse>>
        withContext(Dispatchers.IO) {
            quoteListResponse = remoteDataSource.getQuotesByPagination(pageIndex, pageLimit)
        }
        quoteListResponse.onSuccess { list ->
            return Result.Success(list.map { it.mapToDomainModel() })
        }
        return quoteListResponse as Result.Error
    }


    override suspend fun addNewQuote(quote: Quote): Result<Unit> {
        return withContext(Dispatchers.IO) {
            remoteDataSource.addNewQuote(quote)
        }
    }
}