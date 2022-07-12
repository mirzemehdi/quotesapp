package com.mmk.quotes.data.source.remote

import com.google.firebase.firestore.FirebaseFirestoreException
import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.core.util.NetworkHandler
import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse
import java.util.Date
import kotlin.math.min

class FakeQuotesRemoteDataSource(private val networkHandler: NetworkHandler) :
    QuotesRemoteDataSource {

    private val quotesList = mutableMapOf<String, QuoteResponse>()

    private val exception =
        FirebaseFirestoreException("Some error happened", FirebaseFirestoreException.Code.ABORTED)

    var shouldThrowException: Boolean = false

    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ) = sendRequestIfHasNetworkConnection {
        if (shouldThrowException) throw exception
        val sortedList = quotesList.values.sortedBy { it.id }
        val currentQuoteIndex =
            if (pageIndex == null) -1 else sortedList.indexOf(quotesList[pageIndex])

        val startIndex = currentQuoteIndex + 1
        if (startIndex >= sortedList.size)
            return@sendRequestIfHasNetworkConnection Result.success(emptyList<QuoteResponse>())
        val endIndex = min(pageLimit + startIndex, sortedList.size)
        val quotesResponse = sortedList.subList(startIndex, endIndex)
        Result.success(quotesResponse)
    }

    override suspend fun addNewQuote(newQuoteRequest: NewQuoteRequest) =
        sendRequestIfHasNetworkConnection {
            if (shouldThrowException) throw exception
            quotesList[newQuoteRequest.id] = QuoteResponse(
                newQuoteRequest.id,
                newQuoteRequest.author,
                newQuoteRequest.text,
                Date()
            )
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
