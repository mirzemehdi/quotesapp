package com.mmk.quotesapp.repository

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.mmk.quotesapp.model.Quote
import com.mmk.quotesapp.network.NetworkResource
import com.mmk.quotesapp.utils.QUOTES_COLLECTION
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject


class QuoteRepository @Inject constructor(private val db: FirebaseFirestore) {
    private val quotesCollection = db.collection(QUOTES_COLLECTION)

    suspend fun addNewQuote(quote: Quote): NetworkResource<String> {
        return try {
            val documentReference = quotesCollection.add(quote).await()
            NetworkResource.Success(documentReference.id)

        } catch (e: Exception) {
            NetworkResource.Error(e.message)
        }
    }
}