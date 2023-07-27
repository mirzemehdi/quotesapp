package com.mmk.quotes.data.di

import com.mmk.quotes.data.source.remote.QuotesRemoteDataSource
import com.mmk.quotes.data.source.remote.QuotesRemoteDataSourceImpl
import com.mmk.quotes.data.source.remote.apiservice.Constants.BASE_URL
import com.mmk.quotes.data.source.remote.apiservice.QuotesApiService
import com.mmk.quotes.data.source.remote.apiservice.QuotesApiServiceImpl
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val remoteSourceModule = module {
    single {
        HttpClient() {
            install(Resources)
            defaultRequest {
                url(BASE_URL)
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        prettyPrint = true
                        isLenient = true
                        ignoreUnknownKeys = true
                    }
                )
            }
            install(WebSockets) {
                contentConverter = KotlinxWebsocketSerializationConverter(Json)
            }
        }
    }

    factoryOf(::QuotesApiServiceImpl) bind QuotesApiService::class
    factoryOf(::QuotesRemoteDataSourceImpl) bind QuotesRemoteDataSource::class
}
