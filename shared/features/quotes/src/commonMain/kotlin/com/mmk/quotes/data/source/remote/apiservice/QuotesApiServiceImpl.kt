package com.mmk.quotes.data.source.remote.apiservice

import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse
import dev.gitlive.firebase.firestore.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class QuotesApiServiceImpl(private val quotesCollection: CollectionReference) : QuotesApiService {

    //    @Throws(FirebaseFirestoreException::class)
    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): List<QuoteResponse> {
        val response = getQuery(pageIndex, pageLimit).get()
        return response.documents.map { it.data() }
    }

    override fun observeFirstPageQuotes(pageLimit: Int): Flow<List<QuoteResponse>> {
        val snapshots = getQuery(null, pageLimit).snapshots
        return snapshots.map { snapshot -> snapshot.documents.map { it.data() } }
    }

    //    @Throws(FirebaseFirestoreException::class)
    override suspend fun addNewQuote(quote: NewQuoteRequest) {
        val referenceId = quotesCollection.document.id
        quote.id = referenceId
        quotesCollection.document(referenceId).set(quote)
    }

    private fun getQuery(pageIndex: String?, pageLimit: Int): Query {
        val orderedCollection =
            quotesCollection.orderBy(FieldPath("timeStamp"), Direction.DESCENDING)
        val query = pageIndex?.let { orderedCollection.startAfter(it.toLong()) } ?: orderedCollection
        return query.limit(pageLimit.toLong())
    }
}
