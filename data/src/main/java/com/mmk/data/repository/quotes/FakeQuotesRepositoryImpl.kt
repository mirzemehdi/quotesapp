package com.mmk.data.repository.quotes

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.google.firebase.firestore.CollectionReference
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import com.mmk.domain.repository.QuotesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber


class FakeQuotesRepositoryImpl(private val quotesCollection: CollectionReference) :
    QuotesRepository {

    override suspend fun getQuotesByPagination(): Result<Flow<PagingData<Quote>>> {


//        val quoteFlowList: Flow<PagingData<Quote>>
//        withContext(Dispatchers.IO) {
//            val config =
//                PagingConfig(pageSize = 10, initialLoadSize = 15, enablePlaceholders = false)
//
//
//            val quoteResponseListFlow =
//                Pager(
//                    config = config,
//                    pagingSourceFactory = { QuotesPagingSource(quotesCollection) }).flow
//
//
//            quoteFlowList = quoteResponseListFlow.map { quoteResponsePagedData ->
//                quoteResponsePagedData.map { it.mapToDomainModel() }
//            }
//
//
//        }

        val quoteList = listOf<Quote>(Quote("1", "Author", "Text"), Quote("2", "Author2", "Text2"))
        val pagingData: PagingData<Quote> = PagingData.from(quoteList)
        return Result.Success(flowOf(pagingData))

    }


}