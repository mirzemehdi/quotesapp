package com.mmk.quotes.domain.usecase.addquote

import com.mmk.core.model.Result
import com.mmk.quotes.data.repository.FakeQuotesRepository
import com.mmk.quotes.domain.model.QuoteUtil
import kotlinx.coroutines.test.runTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class AddNewQuoteImplTest {

    private lateinit var quotesRepository: FakeQuotesRepository
    private lateinit var addNewQuote: AddNewQuote

    @BeforeTest
    internal fun setUp() {
        quotesRepository = FakeQuotesRepository()
        addNewQuote = AddNewQuoteImpl(quotesRepository)
    }

    @Test
    fun successResultIsReturned_whenAddedSuccessfully() = runTest {
        val quote = QuoteUtil.newQuoteWithConstantId()
        quotesRepository.shouldReturnSuccessResult = true
        val actual = addNewQuote(quote)
        val expected = Result.EMPTY
        assertEquals(expected, actual)
    }

    @Test
    fun errorResultIsReturned_whenErrorOccurs() = runTest {
        val quote = QuoteUtil.newQuoteWithConstantId()
        quotesRepository.shouldReturnSuccessResult = false
        val actual = addNewQuote(quote)
        val expected = Result.Error()
        assertEquals(expected, actual)
    }
}
