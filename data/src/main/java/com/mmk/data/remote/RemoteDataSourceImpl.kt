package com.mmk.data.remote

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.google.firebase.firestore.CollectionReference
import com.mmk.data.remote.model.response.QuoteResponse
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.tasks.await

class RemoteDataSourceImpl(private val quotesCollection: CollectionReference) : RemoteDataSource {

    override suspend fun getQuotesByPagination(): Result<Flow<PagingData<QuoteResponse>>> {
        val config =
            PagingConfig(pageSize = 10, initialLoadSize = 15, enablePlaceholders = false)
        val quoteResponseListFlow =
            Pager(
                config = config,
                pagingSourceFactory = { QuotesPagingSource(quotesCollection) }).flow

        return Result.Success(quoteResponseListFlow)

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