package com.mmk.quotes.data.source

import com.mmk.quotes.data.model.Quote
import org.litote.kmongo.coroutine.CoroutineDatabase

class QuotesDataSourceImpl(quotesDB: CoroutineDatabase) : QuotesDataSource {

    private val quotes = quotesDB.getCollection<Quote>()

    override suspend fun getQuotesByPagination(pageIndex: String?, pageLimit: Int): List<Quote> {
        //TODO add pagination
        return quotes.find()
            .descendingSort(Quote::timeStamp)
            .toList()
    }

    override suspend fun insertQuote(quote: Quote) {
        quotes.insertOne(quote)
    }
}