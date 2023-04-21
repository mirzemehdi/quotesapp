package com.mmk.quotes.presentation.addnewquote

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mmk.common.ui.UiState
import com.mmk.common.ui.observeEvent
import com.mmk.common.ui.theme.MyApplicationTheme
import com.mmk.common.ui.util.TextFieldState
import com.mmk.quotes.R
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddNewQuoteFragment : Fragment() {
    private val viewModel: AddNewQuoteVM by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
        setContent { AddNewQuoteScreen(viewModel) }
    }

    @Composable
    fun AddNewQuoteScreen(viewModel: AddNewQuoteVM) {
        val uiState by viewModel.uiState.observeAsState()
        val isLoading = uiState is UiState.Loading
        viewModel.onQuoteAdded.observeEvent {
            findNavController().popBackStack()
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
                    .background(MyApplicationTheme.colors.background)
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.title_add_quote),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MyApplicationTheme.colors.onBackground
                )
                OutlinedTextField(
                    value = newQuoteTextFieldState.value,
                    onValueChange = { newQuoteTextFieldState.onValueChanged(it) },
                    label = { Text(text = stringResource(id = R.string.hint_write_your_quote)) },
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .fillMaxWidth()
                )
                OutlinedTextField(
                    value = quoteAuthorTextFieldState.value,
                    onValueChange = { quoteAuthorTextFieldState.onValueChanged(it) },
                    label = { Text(text = stringResource(id = R.string.hint_new_quote_author)) },
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
                        Button(onClick = { onClickAdd() }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = stringResource(id = R.string.text_add_new_quote_btn))
                        }
                    }
                    androidx.compose.animation.AnimatedVisibility(visible = isLoading) {
                        CircularProgressIndicator()
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
}
