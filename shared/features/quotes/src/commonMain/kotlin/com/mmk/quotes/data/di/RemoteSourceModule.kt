package com.mmk.quotes.data.di

import com.mmk.quotes.data.source.remote.QuotesRemoteDataSource
import com.mmk.quotes.data.source.remote.QuotesRemoteDataSourceImpl
import com.mmk.quotes.data.source.remote.apiservice.Constants.QUOTES_COLLECTION_NAME
import com.mmk.quotes.data.source.remote.apiservice.QuotesApiService
import com.mmk.quotes.data.source.remote.apiservice.QuotesApiServiceImpl
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.firestore.FirebaseFirestore
import dev.gitlive.firebase.firestore.firestore
import org.koin.core.qualifier.named
import org.koin.dsl.module

val remoteSourceModule = module {

    single { Firebase.firestore }

    fun provideQuotesReference(db: FirebaseFirestore) =
        db.collection(QUOTES_COLLECTION_NAME)

    single(named(QUOTES_COLLECTION_NAME)) { provideQuotesReference(get()) }

    factory<QuotesApiService> { QuotesApiServiceImpl(get(named(QUOTES_COLLECTION_NAME))) }
    factory<QuotesRemoteDataSource> { QuotesRemoteDataSourceImpl(get(), get()) }
}
