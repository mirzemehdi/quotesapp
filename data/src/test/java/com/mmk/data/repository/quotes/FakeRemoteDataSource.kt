package com.mmk.data.repository.quotes

import androidx.paging.PagingData
import com.mmk.data.remote.RemoteDataSource
import com.mmk.data.remote.model.response.QuoteResponse
import com.mmk.domain.model.Quote
import com.mmk.domain.model.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.util.*

class FakeRemoteDataSource : RemoteDataSource {

    private var mList: MutableList<QuoteResponse>? = mutableListOf()

    override suspend fun getQuotesByPagination(): Result<Flow<PagingData<QuoteResponse>>> {
        return if (mList == null) Result.Error(message = "List is null")
        else return Result.Success(flowOf(PagingData.from(mList!!)))
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