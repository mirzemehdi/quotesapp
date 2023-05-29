package com.mmk.quotes.data.source.remote.apiservice


import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.data.source.remote.model.response.QuoteResponse
import dev.gitlive.firebase.firestore.*

class QuotesApiServiceImpl(private val quotesCollection: CollectionReference) : QuotesApiService {

//    @Throws(FirebaseFirestoreException::class)
    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): List<QuoteResponse> {
        val orderedCollection = quotesCollection.orderBy(FieldPath("id"))
        val query = pageIndex?.let { orderedCollection.startAfter(it) } ?: orderedCollection
        val response = query.limit(pageLimit.toLong()).get()
        return response.documents.map { it.data() }

    }



//    @Throws(FirebaseFirestoreException::class)
    override suspend fun addNewQuote(quote: NewQuoteRequest) {
        val referenceId = quotesCollection.document.id
        quote.id = referenceId
        quotesCollection.document(referenceId).set(quote)
    }
}
