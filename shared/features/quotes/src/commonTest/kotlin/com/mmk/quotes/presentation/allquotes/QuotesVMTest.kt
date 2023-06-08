package com.mmk.quotes.presentation.allquotes

import app.cash.turbine.test
import com.mmk.quotes.data.repository.FakeQuotesRepository
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.model.QuoteUtil
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPagination
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPaginationImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)

internal class QuotesVMTest {

    private lateinit var viewModel: QuotesVM
    private lateinit var getAllQuotesByPagination: GetAllQuotesByPagination
    private lateinit var quotesRepository: FakeQuotesRepository

    @BeforeTest
    internal fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())

        quotesRepository = FakeQuotesRepository()
        getAllQuotesByPagination = GetAllQuotesByPaginationImpl(quotesRepository)
        viewModel = QuotesVM(getAllQuotesByPagination)
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `ui state is in loading when viewModel is created`() = runTest {
        viewModel.getQuotesUiState.test {
            val uiState = awaitItem()
            assertIs<QuotesUiState.Loading>(uiState)
            cancel()
        }
    }

    @Test
    fun `ui state is in empty state when there are no quotes`() = runTest {
        viewModel.getQuotesUiState.test {
            skipItems(1) // Skip loading state
            val uiState = awaitItem()
            assertIs<QuotesUiState.Empty>(uiState)
            cancel()
        }
    }

    @Test
    fun `ui state is in has data state when there are quotes`() = runTest {
        quotesRepository.addNewQuote(Quote())
        viewModel.getQuotesUiState.test {
            skipItems(1) // Skip loading state
            val uiState = awaitItem()
            assertIs<QuotesUiState.HasData>(uiState)
            cancel()
        }
    }

    @Test
    fun `ui state is in has data state and contains quote list when there are quotes`() = runTest {
        QuoteUtil.getQuoteListWithUniqueIds(20).forEach {
            quotesRepository.addNewQuote(quote = it)
        }
        viewModel.getQuotesUiState.test {
            skipItems(1) // Skip loading state
            val uiState = awaitItem()
            assertIs<QuotesUiState.HasData>(uiState)
            val expectedList = (
                (
                    quotesRepository.getQuotesByPagination(
                        null,
                        QuotesVM.NB_INITIAL_QUOTES_SIZE
                    )
                    ) as com.mmk.core.model.Result.Success
                ).data
            assertEquals(expectedList, uiState.quotesList)
            cancel()
        }
    }

    @Test
    fun `ui state is in has data state and pagination is loading when calling loadNext`() = runTest {
        QuoteUtil.getQuoteListWithUniqueIds(20).forEach {
            quotesRepository.addNewQuote(quote = it)
        }
        viewModel.getQuotesUiState.test {
            skipItems(1) // Skip loading state
            var uiState = awaitItem()
            assertIs<QuotesUiState.HasData>(uiState)
            assertFalse(uiState.isPaginationLoading)

            viewModel.loadNextQuotes()

            uiState = awaitItem()
            assertIs<QuotesUiState.HasData>(uiState)
            assertTrue(uiState.isPaginationLoading)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `verify pagination`() = runTest {
        QuoteUtil.getQuoteListWithUniqueIds(20).forEach {
            quotesRepository.addNewQuote(quote = it)
        }
        viewModel.getQuotesUiState.test {
            skipItems(1) // Skip initial loading state
            var uiState = awaitItem()
            assertIs<QuotesUiState.HasData>(uiState)
            assertFalse(uiState.isPaginationLoading)

            val firstPageQuoteList = (
                (
                    quotesRepository.getQuotesByPagination(
                        null,
                        QuotesVM.NB_INITIAL_QUOTES_SIZE
                    )
                    ) as com.mmk.core.model.Result.Success
                ).data

            assertEquals(firstPageQuoteList, uiState.quotesList)

            viewModel.loadNextQuotes()

            uiState = awaitItem()
            assertIs<QuotesUiState.HasData>(uiState)
            assertTrue(uiState.isPaginationLoading)

            uiState = awaitItem()
            assertIs<QuotesUiState.HasData>(uiState)
            assertFalse(uiState.isPaginationLoading)

            val secondPageQuoteList = (
                (
                    quotesRepository.getQuotesByPagination(
                        firstPageQuoteList.last().timeStamp.toString(),
                        QuotesVM.NB_QUOTES_LIMIT_PER_PAGE
                    )
                    ) as com.mmk.core.model.Result.Success
                ).data

            assertEquals(firstPageQuoteList + secondPageQuoteList, uiState.quotesList)

            cancel()
        }
    }

    @Test
    fun `uiState has Error message when getQuotes result is failed`() = runTest {
        quotesRepository.shouldReturnSuccessResult = false
        viewModel.loadNextQuotes()
        viewModel.uiMessage.test {
            val uiMessage = awaitItem()
            assertNotNull(uiMessage)
            cancel()
        }
    }
}
