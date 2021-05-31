package com.mmk.domain.repository

import androidx.paging.PagingData
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import kotlinx.coroutines.flow.Flow

interface QuotesRepository {
    suspend fun getQuotesByPagination(): Result<Flow<PagingData<Quote>>>
}