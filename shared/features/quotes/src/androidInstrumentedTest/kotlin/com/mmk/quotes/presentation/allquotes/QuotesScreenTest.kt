package com.mmk.quotes.presentation.allquotes

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performScrollToIndex
import com.mmk.common.ui.theme.MyApplicationTheme
import com.mmk.core.model.Result
import com.mmk.quotes.data.FakeQuotesRepository
import com.mmk.quotes.domain.model.Quote
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPagination
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPaginationImpl
import io.mockk.coEvery
import io.mockk.spyk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuotesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private lateinit var getAllQuotesByPagination: GetAllQuotesByPagination
    private lateinit var quotesRepository: FakeQuotesRepository
    private lateinit var viewModel: QuotesVM

    @Before
    fun setUp() {
        quotesRepository = FakeQuotesRepository()
        getAllQuotesByPagination = spyk(GetAllQuotesByPaginationImpl(quotesRepository))
        viewModel = QuotesVM(getAllQuotesByPagination = getAllQuotesByPagination)
        composeTestRule.setContent {
            MyApplicationTheme {
                QuotesRoute(viewModel = viewModel)
            }
        }
    }

    @Test
    fun noQuotes_emptyViewIsShown() {
        quotesRepository.shouldReturnEmptyList = true
        composeTestRule.emptyView().assertIsDisplayed()
    }

    @Test
    fun hasQuotes_quoteListIsShown() = runTest {
        val quote = Quote("testId", "testAuthor", text = "testQuote")
        quotesRepository.addNewQuote(quote)
        composeTestRule.onNodeWithText("testAuthor").assertExists()
        composeTestRule.onNodeWithText("testQuote").assertExists()
    }

    @Test
    fun noQuotes_loadNextQuotes_centerProgressIsShown() = runTest {
        coEvery { getAllQuotesByPagination(any(), any()) } coAnswers {
            delay(1000)
            Result.success(listOf())
        }
        viewModel.loadNextQuotes()
        composeTestRule.quotesList().assertDoesNotExist()
        composeTestRule.emptyView().assertDoesNotExist()
        composeTestRule.progressView().assertIsDisplayed() // Center Progress is shown
        composeTestRule.quotesListPaginationLoading()
            .assertDoesNotExist() // Pagination Progress is not shown
    }

    @Test
    fun hasQuotes_loadNextQuotes_paginationProgressIsShownInFooter() = runTest {
        val loadingTimeInMs = 200L
        quotesRepository.fillList(20)
        composeTestRule.mainClock.advanceTimeByFrame()

        coEvery { getAllQuotesByPagination(any(), any()) } coAnswers {
            delay(loadingTimeInMs)
            Result.success(emptyList())
        }
        viewModel.loadNextQuotes()
        composeTestRule.quotesList().performScrollToIndex(QuotesVM.NB_INITIAL_QUOTES_SIZE)

        composeTestRule.quotesList().assertIsDisplayed()
        composeTestRule.progressView().assertDoesNotExist() // Center progress is not shown
        composeTestRule.quotesListPaginationLoading().assertIsDisplayed() // Pagination progress is shown
    }

    @Test
    fun verifyPagination() = runTest {
        val loadingTimeInMs = 200L
        quotesRepository.fillList(20)
        composeTestRule.mainClock.advanceTimeByFrame()

        coEvery { getAllQuotesByPagination(any(), any()) } coAnswers {
            delay(loadingTimeInMs)
            Result.success(listOf(Quote(text = "Author#16")))
        }
        viewModel.loadNextQuotes()
        composeTestRule.quotesList().performScrollToIndex(QuotesVM.NB_INITIAL_QUOTES_SIZE)

        composeTestRule.quotesListPaginationLoading().assertIsDisplayed() // Pagination progress is shown

        // Before loading is finished
        composeTestRule.onNodeWithText("Author#16").assertDoesNotExist()
        Thread.sleep(loadingTimeInMs) // waiting for loadingTime is finished
        // After loading is finished
        composeTestRule.quotesList().performScrollToIndex(16)
        composeTestRule.onNodeWithText("Author#16").assertIsDisplayed()
    }

    private fun ComposeContentTestRule.emptyView() = this.onNodeWithTag("emptyView", true)
    private fun ComposeContentTestRule.quotesList() = this.onNodeWithTag("quotesList")
    private fun ComposeContentTestRule.progressView() = this.onNodeWithTag("quotesProgress")
    private fun ComposeContentTestRule.quotesListPaginationLoading() =
        this.onNodeWithTag("quotesListPaginationProgress")
}
