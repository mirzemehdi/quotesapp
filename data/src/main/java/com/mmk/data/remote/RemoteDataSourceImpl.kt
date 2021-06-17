package com.mmk.data.remote

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FieldPath
import com.mmk.data.remote.model.response.QuoteResponse
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import kotlinx.coroutines.tasks.await
import timber.log.Timber

class RemoteDataSourceImpl(private val quotesCollection: CollectionReference) : RemoteDataSource {

    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<QuoteResponse>> {

        return try {
            val orderedCollection = quotesCollection.orderBy(FieldPath.documentId())
            val query =
                if (pageIndex == null) orderedCollection else orderedCollection.startAfter(pageIndex)
            val response = query.limit(pageLimit.toLong()).get().await()
            val quotesList = response.toObjects(QuoteResponse::class.java)
            Result.Success(quotesList)

        } catch (exception: Exception) {
            Timber.e(exception)
            Result.Error("Unknown error occurred while getting list")
        }
    }

    override suspend fun addNewQuote(quote: Quote): Result<Unit> {
        return try {
            val referenceId = quotesCollection.document().id
            quote.id = referenceId
            quotesCollection.document(referenceId).set(quote).await()
            Result.Success(Unit)

        } catch (e: Exception) {
            Result.Error(e.message)
        }
    }
}