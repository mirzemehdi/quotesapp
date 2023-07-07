package com.mmk.quotes.presentation.addnewquote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import com.mmk.common.ui.MR
import com.mmk.common.ui.components.MyCircularProgressBar
import com.mmk.common.ui.components.MyOutlinedTextField
import com.mmk.common.ui.components.UiMessageOwnerComponent
import com.mmk.common.ui.theme.getColors
import com.mmk.common.ui.util.TextFieldState
import com.mmk.core.util.ViewModelProvider
import com.mmk.core.util.asState
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun AddNewQuoteRoute(viewModel: AddNewQuoteVM = ViewModelProvider.get(), onBackPress: () -> Unit) {
    UiMessageOwnerComponent(uiMessageOwner = viewModel) {
        val uiState by viewModel.uiState.asState()
        LaunchedEffect(key1 = uiState.newAddedQuote) {
            if (uiState.newAddedQuote != null) {
                onBackPress()
                viewModel.reset()
            }
        }
//
        val newQuoteText by viewModel.quoteText.asState()
        val quoteAuthor by viewModel.quoteAuthor.asState()
//
        AddNewQuoteScreen(
            isLoading = uiState.isLoading,
            newQuoteTextFieldState = TextFieldState(
                value = newQuoteText,
                viewModel::onQuoteTextChanged
            ),
            quoteAuthorTextFieldState = TextFieldState(
                value = quoteAuthor,
                viewModel::onAuthorTextChanged
            ),
            onClickAdd = viewModel::addQuote
        )
    }
}

@Composable
private fun AddNewQuoteScreen(
    isLoading: Boolean,
    newQuoteTextFieldState: TextFieldState,
    quoteAuthorTextFieldState: TextFieldState,
    onClickAdd: () -> Unit,
) {

    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(getColors().background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(MR.strings.title_add_quote),

            style = MaterialTheme.typography.headlineLarge,
            color = getColors().onBackground
        )
        MyOutlinedTextField(
            textFieldState = newQuoteTextFieldState,
            label = stringResource(MR.strings.hint_write_your_quote),
            maxLines = 5,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .testTag("addQuoteText")
        )
//
        MyOutlinedTextField(
            textFieldState = quoteAuthorTextFieldState,
            label = stringResource(MR.strings.hint_new_quote_author),
            maxLines = 2,
            modifier = Modifier
                .padding(top = 16.dp)
                .fillMaxWidth()
                .testTag("addQuoteAuthor")
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 24.dp),
            contentAlignment = Alignment.Center
        ) {

            androidx.compose.animation.AnimatedVisibility(isLoading.not()) {
                Button(
                    onClick = { onClickAdd() },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = getColors().secondary,
                        contentColor = getColors().onSecondary
                    ),
                    contentPadding = PaddingValues(16.dp),
                    shape = RoundedCornerShape(4.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("addNewQuoteButton")
                ) {
                    Text(
                        text = stringResource(MR.strings.text_add_new_quote_btn),
                        color = getColors().onSecondary,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }

            androidx.compose.animation.AnimatedVisibility(visible = isLoading) {
                MyCircularProgressBar(
                    modifier = Modifier
                        .testTag("addNewQuoteProgress")
                )
            }
        }
    }
}

// @Preview(showBackground = true)
// @Composable
// private fun AddNewQuote() {
//    MyApplicationTheme {
//        AddNewQuoteScreen(
//            isLoading = false,
//            newQuoteTextFieldState = TextFieldState(),
//            quoteAuthorTextFieldState = TextFieldState(),
//            onClickAdd = {}
//        )
//    }
// }
