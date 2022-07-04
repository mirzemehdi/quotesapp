package com.mmk.quotes.data.source.remote

import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.core.util.NetworkHandler
import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse

class QuotesRemoteDataSourceImpl(private val networkHandler: NetworkHandler) :
    QuotesRemoteDataSource {

    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<QuoteResponse>> {
        if (networkHandler.hasNetworkConnection()
            .not()
        ) return Result.Error(ErrorEntity.NetworkConnection)
        return Result.Error()
    }

    override suspend fun addNewQuote(newQuoteRequest: NewQuoteRequest): Result<Unit> {
        TODO("Not yet implemented")
    }
}
