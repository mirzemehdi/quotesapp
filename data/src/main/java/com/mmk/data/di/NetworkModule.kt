package com.mmk.data.di

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mmk.data.network.NetworkConstants
import org.koin.core.qualifier.named
import org.koin.dsl.module



const val QUALIFIER_QUOTES="quotes"

val networkModule = module {
    single { Firebase.firestore }

    fun provideQuotesReference(db: FirebaseFirestore) =
        db.collection(NetworkConstants.QUOTES_COLLECTION_NAME)

    single(named(QUALIFIER_QUOTES)) { provideQuotesReference(get()) }
}