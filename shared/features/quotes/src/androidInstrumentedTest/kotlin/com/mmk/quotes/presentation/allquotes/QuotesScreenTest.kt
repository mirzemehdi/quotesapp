package com.mmk.quotes.presentation.allquotes

import androidx.compose.ui.test.junit4.createComposeRule
import com.mmk.common.ui.theme.MyApplicationTheme
import com.mmk.quotes.data.FakeQuotesRepository
import com.mmk.quotes.domain.repository.QuotesRepository
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPagination
import com.mmk.quotes.domain.usecase.allquotes.GetAllQuotesByPaginationImpl
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class QuotesScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val tag = "QuotesScreenTag"
    private lateinit var getAllQuotesByPagination: GetAllQuotesByPagination
    private lateinit var quotesRepository: QuotesRepository
    private lateinit var viewModel: QuotesVM

    @Before
    fun setUp() {
        quotesRepository = FakeQuotesRepository()
        getAllQuotesByPagination = GetAllQuotesByPaginationImpl(quotesRepository)
        viewModel = QuotesVM(getAllQuotesByPagination = getAllQuotesByPagination)
    }

    @Test
    fun test() {
        composeTestRule.setContent {
            MyApplicationTheme {
                QuotesRoute(viewModel = viewModel)
            }
            Thread.sleep(2000)
        }
    }
}
