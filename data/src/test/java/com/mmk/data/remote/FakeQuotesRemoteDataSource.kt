package com.mmk.data.remote

import com.mmk.data.remote.quotes.QuotesRemoteDataSource
import com.mmk.data.remote.quotes.model.response.QuoteResponse
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import java.util.*

class FakeQuotesRemoteDataSource : QuotesRemoteDataSource {

    private var mList: MutableList<QuoteResponse>? = mutableListOf()

    override suspend fun getQuotesByPagination(
        pageIndex: String?,
        pageLimit: Int
    ): Result<List<QuoteResponse>> {
        return if (mList == null) Result.Error(message = "List is null")
        else return Result.Success(mList!!)
    }

    override suspend fun addNewQuote(quote: Quote): Result<Unit> {
        return if (mList == null) Result.Error(message = "List is null")
        else {
            mList?.add(
                QuoteResponse(
                    id = UUID.randomUUID().toString(),
                    author = quote.author,
                    text = quote.text
                )
            )
            Result.Success(Unit)
        }
    }

    fun setList(list: List<QuoteResponse>?) {
        if (list == null) mList = null
        else {

            mList = mutableListOf()
            mList?.addAll(list)
        }
    }
}