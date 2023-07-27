package com.mmk.quotes.routes

import com.mmk.quotes.data.model.Quote
import com.mmk.quotes.data.source.QuotesDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.get
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route
import io.ktor.server.websocket.*
import io.ktor.websocket.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.*
import java.util.Collections.synchronizedSet
import java.util.concurrent.CopyOnWriteArrayList
import kotlin.collections.LinkedHashSet

fun Route.configureQuotesRoutes(quotesDataSource: QuotesDataSource) {
    webSocket("/quotes-socket") {
        var job: Job? = null
        try {
            for (frame in incoming) {
                frame as? Frame.Text ?: continue
                try {
                    val pageLimit = Integer.valueOf(frame.readText())
                    job?.cancel()
                    job = launch {
                        val quotesFlow = quotesDataSource.listenForFirstPageDataChanges(pageLimit = pageLimit)
                        quotesFlow.collectLatest {
                            send(Frame.Text(Json.encodeToString(it)))
                        }
                    }
                } catch (e: NumberFormatException) {
                    send(Frame.Text("Page limit should be number"))
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            job?.cancel()
            this.close()
        }
    }

    get<QuoteResources> {
        call.respond(
            HttpStatusCode.OK,
            quotesDataSource.getQuotesByPagination(
                pageIndex = it.pageIndex,
                pageLimit = it.pageLimit
            )
        )
    }

    post<QuoteResources> {
        // Add New Quote.
        val quote = call.receive<Quote>()
        quotesDataSource.insertQuote(quote)
        call.respond(HttpStatusCode.Created)
    }

    get<QuoteResources.Id> { quote ->
        // Show a quote with id ${quote.id} ...
        call.respondText("Not Implemented: A quote with id ${quote.id}", status = HttpStatusCode.OK)
    }

    put<QuoteResources.Id> { quote ->
        // Update a quote ...
        call.respondText("Not implemented: A quote with id ${quote.id} updated", status = HttpStatusCode.OK)
    }
}