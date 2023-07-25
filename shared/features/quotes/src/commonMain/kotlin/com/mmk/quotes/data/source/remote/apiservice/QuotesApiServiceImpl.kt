package com.mmk.quotes.data.source.remote.apiservice

import com.mmk.core.util.logger.AppLogger
import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse
import dev.gitlive.firebase.firestore.CollectionReference
import dev.gitlive.firebase.firestore.Direction
import dev.gitlive.firebase.firestore.FieldPath
import dev.gitlive.firebase.firestore.Query
import dev.gitlive.firebase.firestore.orderBy
import dev.gitlive.firebase.firestore.startAfter
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuotesApiServiceImpl(
    private val quotesCollection: CollectionReference,
    private val httpClient: HttpClient,
) : QuotesApiService {

    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int,
    ): List<QuoteResponse> = networkApiCall {
        httpClient.get("/quotes") {
            url {
                pageIndex?.let {
                    parameters.append("pageIndex", it)
                }
                parameters.append("pageLimit", pageLimit.toString())
            }
        }.body()
    }

    override fun observeFirstPageQuotes(pageLimit: Int): Flow<List<QuoteResponse>> {
        val snapshots = getQuery(null, pageLimit).snapshots
        return snapshots.map { snapshot -> snapshot.documents.map { it.data() } }
    }

    override suspend fun addNewQuote(quote: NewQuoteRequest) = networkApiCall {
        httpClient.post("/quotes") {
            contentType(ContentType.Application.Json)
            setBody(quote)
        }
        Unit
    }

    private fun getQuery(pageIndex: String?, pageLimit: Int): Query {
        val orderedCollection =
            quotesCollection.orderBy(FieldPath("timeStamp"), Direction.DESCENDING)
        val query =
            pageIndex?.let { orderedCollection.startAfter(it.toLong()) } ?: orderedCollection
        return query.limit(pageLimit.toLong())
    }

    private suspend fun <T> networkApiCall(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: Exception) {
            AppLogger.e(e.toString())
            throw ApiServiceException(e)
        }
    }
}
