package com.mmk.quotes.data.repository

import com.mmk.core.model.Result
import com.mmk.core.model.mapDataOnSuccess
import com.mmk.core.util.BackgroundExecutor
import com.mmk.quotes.data.mappers.QuoteMapper
import com.mmk.quotes.data.source.remote.QuotesRemoteDataSource
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.repository.QuotesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class QuotesRepositoryImpl(
    private val quotesRemoteDataSource: QuotesRemoteDataSource,
    private val quoteMapper: QuoteMapper,
    private val backgroundExecutor: BackgroundExecutor = BackgroundExecutor.IO
) :
    QuotesRepository {
    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<Quote>> = backgroundExecutor.execute {
        val result = quotesRemoteDataSource.getQuotesByPagination(pageIndex, pageLimit)
        result.mapDataOnSuccess { quoteResponseList ->
            quoteResponseList.map { it.mapToDomainModel() }
        }
    }

    override fun listenForDataChange(pageLimit: Int): Flow<Result<List<Quote>>> {
        return quotesRemoteDataSource
            .observeFirstPageQuotes(pageLimit)
            .map { result ->
                result.mapDataOnSuccess { quoteResponseList ->
                    quoteResponseList.map { it.mapToDomainModel() }
                }
            }
            .flowOn(backgroundExecutor.dispatcher)
    }

    override suspend fun addNewQuote(quote: Quote): Result<Unit> = backgroundExecutor.execute {
        quotesRemoteDataSource.addNewQuote(quoteMapper.mapToNetworkRequest(quote))
    }
}
