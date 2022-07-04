package com.mmk.quotes.data.mappers

import com.google.common.truth.Truth.assertThat
import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.domain.model.Quote
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import java.util.UUID

internal class QuoteMapperTest {

    private lateinit var quoteMapper: QuoteMapper

    @BeforeEach
    fun setUp() {
        quoteMapper = QuoteMapper()
    }

    @Test
    @DisplayName("Quote domain object mapped to network request object correctly")
    fun testDomainObjIsMappedToNetworkObject() {
        val id = UUID.randomUUID().toString()
        val text = "Test Text"
        val author = "Test Author"
        val quote = Quote(id, author, text)
        val expectedNewQuoteRequest = NewQuoteRequest(id, author, text)
        assertThat(quoteMapper.mapToNetworkRequest(quote)).isEqualTo(expectedNewQuoteRequest)
    }
}
