package com.mmk.plugins

import com.mmk.quotes.data.source.QuotesDataSource
import com.mmk.quotes.routes.configureQuotesRoutes
import io.ktor.server.routing.*
import io.ktor.server.response.*
import io.ktor.server.application.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
    routing {
        val quotesDataSource: QuotesDataSource by inject()
        get("/") {
            call.respondText("Hello To Quotes Page!")
        }

        configureQuotesRoutes(quotesDataSource = quotesDataSource)
    }
}
