package com.mmk.data.repository.quotes

import androidx.paging.PagingData
import androidx.paging.map
import com.mmk.data.remote.RemoteDataSource
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.model.onSuccess
import com.mmk.domain.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext


class QuotesRepositoryImpl(private val remoteDataSource: RemoteDataSource) : QuotesRepository {

    override suspend fun getQuotesByPagination(): Result<Flow<PagingData<Quote>>> {
        val quoteResponseListFlow = remoteDataSource.getQuotesByPagination()
        quoteResponseListFlow.onSuccess { response ->
            val quoteFlowList = response.map { quoteResponsePagedData ->
                quoteResponsePagedData.map { it.mapToDomainModel() }
            }
            return Result.Success(quoteFlowList)
        }

        return Result.Error("Unknown error")
    }


    override suspend fun addNewQuote(quote: Quote): Result<Unit> {
        return withContext(Dispatchers.IO) {
            remoteDataSource.addNewQuote(quote)
        }
    }
}