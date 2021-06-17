package com.mmk.data.remote

import com.mmk.data.remote.model.response.QuoteResponse
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result

interface RemoteDataSource {

    suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<QuoteResponse>>

    suspend fun addNewQuote(quote: Quote): Result<Unit>
}