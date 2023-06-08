package com.mmk.quotes.data.source.remote.model.response

import com.mmk.quotes.domain.model.QuoteUtil
import kotlin.test.Test
import kotlin.test.assertEquals

internal class QuoteResponseMappingTest {
    private val text = "Test Text"
    private val author = "Test Author"

    @Test
    fun givenId_responseMappedToDomainObjectWithGivenId() {
        val quoteResponse = QuoteResponse(id = QuoteUtil.ID)
        val expectedDomainQuoteId = QuoteUtil.ID
        val actualId = quoteResponse.mapToDomainModel().id
        assertEquals(expectedDomainQuoteId, actualId)
    }

    @Test
    fun verify_responseMappedToDomainObject() {
        val quoteResponse = QuoteResponse(
            id = QuoteUtil.ID,
            author = author,
            text = text,
        )

        val expectedDomainQuote = QuoteUtil.newQuoteWithConstantId().copy(
            author = author,
            text = text,
            isLiked = false
        )
        val actual = quoteResponse.mapToDomainModel()
        assertEquals(expectedDomainQuote, actual)
    }
}
