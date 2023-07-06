package com.mmk.quotes.presentation.addnewquote

import androidx.compose.ui.test.junit4.createComposeRule
import com.mmk.common.ui.theme.MyApplicationTheme
import com.mmk.quotes.domain.usecase.addquote.AddNewQuote
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AddNewQuoteScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val tag = "AddNewQuoteScreenTag"
    private lateinit var addNewQuote: AddNewQuote
    private lateinit var viewModel: AddNewQuoteVM

    @Before
    fun setUp() {
        addNewQuote = mockk()
        viewModel = AddNewQuoteVM(addNewQuoteUseCase = addNewQuote)
    }

    @Test
    fun test() {
        composeTestRule.setContent {
            MyApplicationTheme {
                AddNewQuoteRoute(viewModel = viewModel, onBackPress = {})
            }
        }
    }
}
