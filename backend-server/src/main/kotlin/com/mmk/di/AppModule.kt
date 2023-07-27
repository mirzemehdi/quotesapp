package com.mmk.di

import com.mmk.quotes.data.source.QuotesDataSource
import com.mmk.quotes.data.source.QuotesDataSourceImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

val appModule = module {
    single<CoroutineDatabase> {
        val connectionString = System.getenv("MONGODB_CONNECTION_STRING")
        val mongoClient =
            if (connectionString.isNullOrEmpty()) KMongo.createClient() //local mongo database
            else KMongo.createClient(connectionString)

        mongoClient
            .coroutine
            .getDatabase("quotesDB")
    }
    singleOf(::QuotesDataSourceImpl) bind QuotesDataSource::class
}