package com.mmk.quotes.data.source.remote.apiservice

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestoreException
import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse
import kotlinx.coroutines.tasks.await

class QuotesApiServiceImpl(private val quotesCollection: CollectionReference) : QuotesApiService {

    @Throws(FirebaseFirestoreException::class)
    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): List<QuoteResponse> {
        val orderedCollection = quotesCollection.orderBy(FieldPath.documentId())
        val query = pageIndex?.let { orderedCollection.startAfter(it) } ?: orderedCollection
        val response = query.limit(pageLimit.toLong()).get().await()
        return response.toObjects(QuoteResponse::class.java)
    }

    @Throws(FirebaseFirestoreException::class)
    override suspend fun addNewQuote(quote: NewQuoteRequest) {
        val referenceId = quotesCollection.document().id
        quote.id = referenceId
        quotesCollection.document(referenceId).set(quote).await()
    }
}
