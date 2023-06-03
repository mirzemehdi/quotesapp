package com.mmk.quotes.data.source.remote

import com.google.firebase.firestore.FirebaseFirestoreException
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.core.util.NetworkHandler
import com.mmk.quotes.data.source.remote.apiservice.QuotesApiService
import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest

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
        } catch (e: FirebaseFirestoreException) {
            Result.error(ErrorEntity.apiError(exception = e))
        }
    }
}
