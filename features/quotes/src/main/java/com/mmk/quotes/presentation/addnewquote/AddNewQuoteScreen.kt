package com.mmk.quotes.presentation.addnewquote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mmk.common.ui.UiState
import com.mmk.common.ui.components.MyCircularProgressBar
import com.mmk.common.ui.components.MyOutlinedTextField
import com.mmk.common.ui.observeEvent
import com.mmk.common.ui.theme.MyApplicationTheme
import com.mmk.common.ui.util.TextFieldState
import com.mmk.quotes.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun AddNewQuoteScreen(viewModel: AddNewQuoteVM = koinViewModel(), onBackPress: () -> Unit) {
    val uiState by viewModel.uiState.observeAsState()
    val isLoading = uiState is UiState.Loading
    viewModel.onQuoteAdded.observeEvent {
        onBackPress()
    }
    val newQuoteText = viewModel.quoteText.observeAsState().value ?: ""
    val quoteAuthor = viewModel.quoteAuthor.observeAsState().value ?: ""

    AddNewQuoteScreen(
        isLoading = isLoading,
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

@Composable
private fun AddNewQuoteScreen(
    isLoading: Boolean,
    newQuoteTextFieldState: TextFieldState,
    quoteAuthorTextFieldState: TextFieldState,
    onClickAdd: () -> Unit
) {
    MyApplicationTheme {

        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(MyApplicationTheme.colors.background)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(id = R.string.title_add_quote),
                style = MaterialTheme.typography.headlineLarge,
                color = MyApplicationTheme.colors.onBackground
            )
            MyOutlinedTextField(
                textFieldState = newQuoteTextFieldState,
                label = stringResource(id = R.string.hint_write_your_quote),
                maxLines = 5,
                modifier = Modifier
                    .padding(top = 24.dp)
                    .fillMaxWidth()
            )

            MyOutlinedTextField(
                textFieldState = quoteAuthorTextFieldState,
                label = stringResource(id = R.string.hint_new_quote_author),
                maxLines = 2,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
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
                            backgroundColor =
                            MyApplicationTheme.colors.secondary,
                            contentColor =
                            MyApplicationTheme.colors.onSecondary
                        ),
                        contentPadding = PaddingValues(16.dp),
                        shape = RoundedCornerShape(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = stringResource(id = R.string.text_add_new_quote_btn),
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }

                androidx.compose.animation.AnimatedVisibility(visible = isLoading) {
                    MyCircularProgressBar()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AddNewQuote() {
    MyApplicationTheme {
        AddNewQuoteScreen(
            isLoading = false,
            newQuoteTextFieldState = TextFieldState(),
            quoteAuthorTextFieldState = TextFieldState(),
            onClickAdd = {}
        )
    }
}
