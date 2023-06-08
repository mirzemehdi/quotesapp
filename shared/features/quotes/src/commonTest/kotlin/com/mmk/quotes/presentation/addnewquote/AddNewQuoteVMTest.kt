package com.mmk.quotes.presentation.addnewquote

import app.cash.turbine.test
import com.mmk.quotes.data.repository.FakeQuotesRepository
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.usecase.addquote.AddNewQuote
import com.mmk.quotes.domain.usecase.addquote.AddNewQuoteImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)

internal class AddNewQuoteVMTest {

    private lateinit var viewModel: AddNewQuoteVM
    private lateinit var addNewQuoteUseCase: AddNewQuote
    private lateinit var quotesRepository: FakeQuotesRepository

    @BeforeTest
    internal fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        quotesRepository = FakeQuotesRepository()
        addNewQuoteUseCase = AddNewQuoteImpl(quotesRepository)
        viewModel = AddNewQuoteVM(addNewQuoteUseCase)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `addQuote ui state is not in loading when viewModel is created`() = runTest {
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertFalse(uiState.isLoading)
            cancel()
        }
    }

    @Test
    fun `addQuote ui state newAddedQuote is null when viewModel is created`() = runTest {
        viewModel.uiState.test {
            val uiState = awaitItem()
            assertNull(uiState.newAddedQuote)
            cancel()
        }
    }

    @Test
    fun `addQuote UiState is in Loading when addNewQuote is called`() = runTest {
        quotesRepository.shouldReturnSuccessResult = false
        viewModel.addQuote()
        viewModel.uiState.test {
            skipItems(1)
            val uiState = awaitItem()
            assertTrue(uiState.isLoading)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `addQuote UiState is not loading when addNewQuote result is returned successfully`() = runTest {
        quotesRepository.shouldReturnSuccessResult = true
        viewModel.addQuote()
        viewModel.uiState.test {
            delay(1000) // Skipping intermediate events
            val uiState = expectMostRecentItem()
            assertFalse(uiState.isLoading)
            assertNotNull(uiState.newAddedQuote)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `quote result is not null when addNewQuote result is returned successfully`() = runTest {
        val author = "testAuthor"
        val text = "testText"
        val quote = Quote(author = author, text = text)
        viewModel.onAuthorTextChanged(author)
        viewModel.onQuoteTextChanged(text)
        viewModel.addQuote()
        viewModel.uiState.test {
            delay(1000) // For skipping intermediate ui states
            val uiState = expectMostRecentItem()
            assertNotNull(uiState.newAddedQuote)
            assertEquals(quote.text, uiState.newAddedQuote!!.text)
            assertEquals(quote.author, uiState.newAddedQuote!!.author)
            cancel()
        }
    }

    @Test
    fun `addQuote UiState has Error message when addNewQuote result is failed`() = runTest {
        viewModel.onQuoteTextChanged("text")
        viewModel.onAuthorTextChanged("author")
        quotesRepository.shouldReturnSuccessResult = false
        viewModel.addQuote()
        viewModel.uiMessage.test {
            val uiMessage = awaitItem()
            assertNotNull(uiMessage)
            cancel()
        }
    }
}
