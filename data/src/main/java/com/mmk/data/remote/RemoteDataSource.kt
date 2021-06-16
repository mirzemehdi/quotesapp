package com.mmk.data.remote

import androidx.paging.PagingData
import com.mmk.data.remote.model.response.QuoteResponse
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface RemoteDataSource {

    suspend fun getQuotesByPagination(): Result<Flow<PagingData<QuoteResponse>>>
    suspend fun addNewQuote(quote: Quote): Result<Unit>
}