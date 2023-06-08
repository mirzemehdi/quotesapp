package com.mmk.quotes.domain.model

object QuoteUtil {
    const val ID = "testID"
    const val TIMESTAMP = 0L

    fun newQuoteWithConstantId() = Quote(id = ID)
    fun newQuoteWithConstantIdAndTimeStamp() = Quote(id = ID, timeStamp = TIMESTAMP)

    fun getQuoteListWithUniqueIds(nbQuotes: Int = 10) =
        (1..nbQuotes).map { Quote(id = "testId#$it") }
}
