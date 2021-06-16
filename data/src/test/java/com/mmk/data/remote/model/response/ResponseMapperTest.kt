package com.mmk.data.remote.model.response

import com.google.common.truth.Truth.assertThat
import com.mmk.domain.model.Quote
import org.junit.Test
import java.util.*

class ResponseMapperTest {

    @Test
    fun `mapping QuoteResponse to Quote domain return notLikedQuote`() {
        val quoteResponse = QuoteResponse("1", "Mirzemehdi", "Test", null)
        val quoteDomain = Quote("1", "Mirzemehdi", "Test", false)
        assertThat(quoteResponse.mapToDomainModel()).isEqualTo(quoteDomain)

    }

    @Test
    fun `mapping QuoteResponse to Quote domain should not return liked Quote`() {
        val quoteResponse = QuoteResponse("1", "Mirzemehdi", "Test", null)
        val quoteDomain = Quote("1", "Mirzemehdi", "Test", true)
        assertThat(quoteResponse.mapToDomainModel().isLiked).isFalse()
        assertThat(quoteResponse.mapToDomainModel()).isNotEqualTo(quoteDomain)

    }

    @Test
    fun `mapping QuoteResponse with data!=null to Quote domain`() {
        val quoteResponse = QuoteResponse("1", "Mirzemehdi", "Test", Date())
        val quoteDomain = Quote("1", "Mirzemehdi", "Test", false)
        assertThat(quoteResponse.mapToDomainModel()).isEqualTo(quoteDomain)

    }


}