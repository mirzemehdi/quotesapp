package com.mmk.quotes.data.source

import com.mmk.quotes.data.model.Quote
import com.mongodb.client.model.Aggregates
import com.mongodb.client.model.Filters
import com.mongodb.client.model.changestream.FullDocument
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase

class QuotesDataSourceImpl(quotesDB: CoroutineDatabase) : QuotesDataSource {

    private val quotes = quotesDB.getCollection<Quote>("quote")

    override fun listenForFirstPageDataChanges(pageLimit: Int): Flow<List<Quote>> {
        return quotes.watch<Quote>()
            .fullDocument(FullDocument.DEFAULT)
            .toFlow().map {
                getQuotesByPagination(pageLimit = pageLimit)
            }.onStart {
                emit(getQuotesByPagination(pageLimit = pageLimit))
            }

    }

    override suspend fun getQuotesByPagination(pageIndex: String?, pageLimit: Int): List<Quote> {
        val sortedList = quotes.find().descendingSort(Quote::timeStamp)
        val cursor = if (pageIndex == null) sortedList else sortedList.filter(Filters.lt("_id", pageIndex))
        return cursor
            .limit(pageLimit)
            .toList()
    }

    override suspend fun insertQuote(quote: Quote) {
        val quoteWithId = quote.copy(id = ObjectId().toString()) //Assigning new ID
        quotes.insertOne(quoteWithId)
    }
}