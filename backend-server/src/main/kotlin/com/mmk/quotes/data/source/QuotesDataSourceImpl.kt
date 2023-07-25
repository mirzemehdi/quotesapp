package com.mmk.quotes.data.source

import com.mmk.quotes.data.model.Quote
import com.mongodb.client.model.Filters
import org.bson.types.ObjectId
import org.litote.kmongo.coroutine.CoroutineDatabase

class QuotesDataSourceImpl(quotesDB: CoroutineDatabase) : QuotesDataSource {

    private val quotes = quotesDB.getCollection<Quote>()

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