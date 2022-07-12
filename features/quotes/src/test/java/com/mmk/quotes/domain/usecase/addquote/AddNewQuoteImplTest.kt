package com.mmk.quotes.domain.usecase.addquote

import com.google.common.truth.Truth.assertThat
import com.mmk.core.model.Result
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.repository.QuotesRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@ExperimentalCoroutinesApi
internal class AddNewQuoteImplTest {

    private lateinit var quotesRepository: QuotesRepository
    private lateinit var addNewQuote: AddNewQuote

    @BeforeEach
    internal fun setUp() {
        quotesRepository = mockk()
        addNewQuote = AddNewQuoteImpl(quotesRepository)
    }

    @DisplayName("Success result with Empty data is returned, when added successfully")
    @Test
    fun successResultIsReturned_whenAddedSuccessfully() = runTest {
        val quote = Quote("1")
        coEvery { quotesRepository.addNewQuote(quote) } returns Result.EMPTY
        val result = addNewQuote(quote)
        assertThat(result).isEqualTo(Result.EMPTY)
    }

    @DisplayName("Error result is returned, when there is an error")
    @Test
    fun errorResultIsReturned_whenThereIsAnError() = runTest {
        val quote = Quote("1")
        coEvery { quotesRepository.addNewQuote(quote) } returns Result.Error()
        val result = addNewQuote(quote)
        assertThat(result).isInstanceOf(Result.Error::class.java)
    }
}
