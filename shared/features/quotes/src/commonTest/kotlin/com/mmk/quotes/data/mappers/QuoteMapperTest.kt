package com.mmk.quotes.data.mappers

import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.domain.model.QuoteUtil
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class QuoteMapperTest {

    private lateinit var quoteMapper: QuoteMapper

    @BeforeTest
    fun setUp() {
        quoteMapper = QuoteMapper()
    }

    @Test
    fun verifyDomainObjIsMappedToNetworkObject() {
        val text = "Test Text"
        val author = "Test Author"
        val quote = QuoteUtil.newQuoteWithConstantIdAndTimeStamp().copy(text = text, author = author)
        val expectedNewQuoteRequest = NewQuoteRequest(QuoteUtil.ID, author, text, QuoteUtil.TIMESTAMP)
        val actual = quoteMapper.mapToNetworkRequest(quote)
        assertEquals(expectedNewQuoteRequest, actual)
    }
}
