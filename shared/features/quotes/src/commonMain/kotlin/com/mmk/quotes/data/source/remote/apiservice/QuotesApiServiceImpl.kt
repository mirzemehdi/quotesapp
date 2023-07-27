package com.mmk.quotes.data.source.remote.apiservice

import com.mmk.core.util.logger.AppLogger
import com.mmk.quotes.data.source.remote.apiservice.Constants.BASE_SOCKET_URL
import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.websocket.webSocketSession
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.utils.io.CancellationException
import io.ktor.websocket.DefaultWebSocketSession
import io.ktor.websocket.Frame
import io.ktor.websocket.close
import io.ktor.websocket.readText
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.serialization.json.Json

class QuotesApiServiceImpl(private val httpClient: HttpClient) : QuotesApiService {

    private var quotesSocket: DefaultWebSocketSession? = null

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

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun observeFirstPageQuotes(pageLimit: Int): Flow<List<QuoteResponse>> {
        return flowOf(Unit)
            .onStart {
                initSocketConnection()
                quotesSocket?.send(Frame.Text("$pageLimit"))
            }
            .flatMapLatest {
                quotesSocket?.incoming?.receiveAsFlow() ?: flowOf(Frame.Text(""))
            }
            .filter {
                it is Frame.Text
            }
            .map {
                val json = (it as Frame.Text).readText()
                Json.decodeFromString<List<QuoteResponse>>(json)
            }
            .catch {
                emit(getQuotesByPagination(null, pageLimit))
            }
            .onCompletion {
                if (it is CancellationException) {
                    closeSocketConnection()
                }
            }
    }

    override suspend fun addNewQuote(quote: NewQuoteRequest) = networkApiCall {
        httpClient.post("/quotes") {
            contentType(ContentType.Application.Json)
            setBody(quote)
        }
        Unit
    }

    private suspend fun initSocketConnection() {
        try {
            if (quotesSocket == null)
                quotesSocket =
                    httpClient
                        .webSocketSession(host = BASE_SOCKET_URL, path = "/quotes-socket")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun closeSocketConnection() {
        quotesSocket?.close()
        quotesSocket = null
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
