package com.mmk.quotes.data.source.remote.model.response

import com.google.common.truth.Truth.assertThat
import com.mmk.quotes.domain.model.Quote
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.*

internal class QuoteResponseMappingTest {
    private val id = UUID.randomUUID().toString()
    private val text = "Test Text"
    private val author = "Test Author"

    @Test
    @DisplayName("Given ID, response mapped to domain object with given ID correctly")
    fun givenId_responseMappedToDomainObjectWithGivenId() {
        val quoteResponse = QuoteResponse(id = id)
        val expectedDomainQuoteId = Quote(id = id).id
        assertThat(quoteResponse.mapToDomainModel().id).isEqualTo(expectedDomainQuoteId)
    }

    @Test
    @DisplayName("When there is a date, response mapped to domain object correctly")
    fun whenThereIsADate_responseMappedToDomainObject() {

        val quoteResponse = QuoteResponse(
            id = id,
            author = author,
            text = text,
            createdDate = Date()
        )

        val expectedDomainQuote = Quote(
            id = id,
            author = author,
            text = text,
            isLiked = false
        )

        assertThat(quoteResponse.mapToDomainModel()).isEqualTo(expectedDomainQuote)
    }

    @Test
    @DisplayName("When there is no date, response mapped to domain object correctly")
    fun whenThereIsNoDate_responseMappedToDomainObject() {
        val quoteResponse = QuoteResponse(
            id = id,
            author = author,
            text = text,
            createdDate = null
        )

        val expectedDomainQuote = Quote(
            id = id,
            author = author,
            text = text,
            isLiked = false
        )

        assertThat(quoteResponse.mapToDomainModel()).isEqualTo(expectedDomainQuote)
    }
}
