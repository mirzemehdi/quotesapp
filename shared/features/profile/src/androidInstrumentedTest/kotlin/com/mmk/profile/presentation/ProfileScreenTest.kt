package com.mmk.profile.presentation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import com.mmk.common.ui.theme.MyApplicationTheme
import org.junit.Rule
import org.junit.Test

class ProfileScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()
    private val tag = "ProfileScreenTag"

    @Test
    fun nameIsShownCorrectly() {
        val expectedName = "Mirzamehdi Karimov"
        composeTestRule.setContent {
            MyApplicationTheme {
                ProfileScreen()
            }
        }
        composeTestRule.onRoot().printToLog(tag)
        composeTestRule
            .onNodeWithText(expectedName)
            .assertExists()
    }

    @Test
    fun bioIsShownCorrectly() {
        val expected = "Android Developer"
        composeTestRule.setContent {
            MyApplicationTheme {
                ProfileScreen()
            }
        }
        composeTestRule.onRoot().printToLog(tag)
        composeTestRule
            .onNodeWithText(expected)
            .assertExists()
    }
}
