package com.mmk.quotes.routes

import com.mmk.quotes.data.model.Quote
import com.mmk.quotes.data.source.QuotesDataSource
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.resources.*
import io.ktor.server.resources.post
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.configureQuotesRoutes(quotesDataSource: QuotesDataSource) {
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
        val quote=call.receive<Quote>()
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