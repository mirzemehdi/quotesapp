package com.mmk.quotes.data.di

import com.mmk.quotes.data.source.remote.QuotesRemoteDataSource
import com.mmk.quotes.data.source.remote.QuotesRemoteDataSourceImpl
import com.mmk.quotes.data.source.remote.apiservice.Constants.BASE_URL
import com.mmk.quotes.data.source.remote.apiservice.Constants.QUOTES_COLLECTION_NAME
import com.mmk.quotes.data.source.remote.apiservice.QuotesApiService
import com.mmk.quotes.data.source.remote.apiservice.QuotesApiServiceImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.resources.Resources
import io.ktor.serialization.kotlinx.json.json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val remoteSourceModule = module {

    single { Firebase.firestore }
    single {
        HttpClient(CIO) {
            install(Resources)
            defaultRequest {
                url(BASE_URL)
            }
            install(Logging) {
                level = LogLevel.ALL
            }
            install(ContentNegotiation) {
                json()
            }
        }
    }

    fun provideQuotesReference(db: FirebaseFirestore) =
        db.collection(QUOTES_COLLECTION_NAME)

    single(named(QUOTES_COLLECTION_NAME)) { provideQuotesReference(get()) }

    factory<QuotesApiService> { QuotesApiServiceImpl(get(named(QUOTES_COLLECTION_NAME)), get()) }
    factory<QuotesRemoteDataSource> { QuotesRemoteDataSourceImpl(get(), get()) }
}
