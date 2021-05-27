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
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import timber.log.Timber


class QuotesRepositoryImpl(private val quotesCollection: CollectionReference) : QuotesRepository {

    override suspend fun getQuotesByPagination(): Result<Flow<PagingData<Quote>>> {


        val config =
            PagingConfig(pageSize = 10, initialLoadSize = 15, enablePlaceholders = false)
        val quoteResponseListFlow =
            Pager(
                config = config,
                pagingSourceFactory = { QuotesPagingSource(quotesCollection) }).flow


        val quoteFlowList = quoteResponseListFlow.map { quoteResponsePagedData ->
            quoteResponsePagedData.map {
                it.mapToDomainModel()
            }
        }


        return Result.Success(quoteFlowList)


    }


}