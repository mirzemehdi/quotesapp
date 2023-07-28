package com.mmk.quotes.data.source.remote

import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.core.util.NetworkHandler
import com.mmk.quotes.data.source.remote.apiservice.ApiServiceException
import com.mmk.quotes.data.source.remote.apiservice.QuotesApiService
import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class QuotesRemoteDataSourceImpl(
    private val networkHandler: NetworkHandler,
    private val quotesApiService: QuotesApiService
) :
    QuotesRemoteDataSource {

    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ) = sendRequestIfHasNetworkConnection {
        val quotesResponse = quotesApiService.getQuotesByPagination(pageIndex, pageLimit)
        Result.success(quotesResponse)
    }

    override fun observeFirstPageQuotes(pageLimit: Int): Flow<Result<List<QuoteResponse>>> {
        return quotesApiService
            .observeFirstPageQuotes(pageLimit)
            .map { Result.success(it) }
            .catch {
                emit(Result.error(ErrorEntity.apiError(exception = Exception(it))))
            }
    }

    override suspend fun addNewQuote(newQuoteRequest: NewQuoteRequest) =
        sendRequestIfHasNetworkConnection {
            quotesApiService.addNewQuote(newQuoteRequest)
            Result.EMPTY
        }

    private suspend fun <T> sendRequestIfHasNetworkConnection(
        onHasNetworkConnection: suspend () -> Result<T>
    ): Result<T> {
        if (networkHandler.hasNetworkConnection().not())
            return Result.error(ErrorEntity.networkConnection())
        return try {
            onHasNetworkConnection()
        } catch (apiServiceException: ApiServiceException) {
            Result.error(ErrorEntity.apiError(exception = apiServiceException.e))
        }
    }
}
