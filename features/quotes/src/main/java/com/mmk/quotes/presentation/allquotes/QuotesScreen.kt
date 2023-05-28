package com.mmk.quotes.presentation.allquotes

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mmk.common.ui.components.MyCircularProgressBar
import com.mmk.common.ui.components.UiMessageOwnerComponent
import com.mmk.common.ui.theme.MyApplicationTheme
import com.mmk.quotes.R
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuotesScreen(viewModel: QuotesVM = koinViewModel()) {
    UiMessageOwnerComponent(uiMessageOwner = viewModel) {
        val uiState by viewModel.getQuotesUiState.collectAsStateWithLifecycle()
        QuotesScreen(uiState = uiState, onLoadNextPage = viewModel::loadQuotes)
    }
}

@Composable
private fun QuotesScreen(uiState: QuotesUiState, onLoadNextPage: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.title_quotes),
            style = MaterialTheme.typography.headlineLarge,
            color = MyApplicationTheme.colors.onBackground,
            modifier = Modifier.padding(top = 16.dp)
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            when (uiState) {
                is QuotesUiState.HasData -> QuotesDataList(
                    quotesHasDataUiState = uiState,
                    onLoadNextPage = onLoadNextPage,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 16.dp)
                )
                QuotesUiState.Loading -> MyCircularProgressBar()
                QuotesUiState.Empty -> EmptyQuotesView()
            }
        }
    }
}

@Composable
fun QuotesDataList(
    quotesHasDataUiState: QuotesUiState.HasData,
    onLoadNextPage: () -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(0.dp)) {

        items(quotesHasDataUiState.quotesList.size) { index ->
            val quoteItem = quotesHasDataUiState.quotesList[index]
            if (hasScrolledToEnd(index, quotesHasDataUiState)) {
                onLoadNextPage()
            }

            val context = LocalContext.current
            QuoteItem(
                quote = quoteItem,
                onClickItem = {
                    Toast.makeText(context, quoteItem.text, Toast.LENGTH_SHORT).show()
                },
                onClickLikeButton = {
                    Toast.makeText(context, "Clicked Like button", Toast.LENGTH_SHORT)
                        .show()
                },
                onClickShareButton = {
                    Toast.makeText(context, "Clicked Share button", Toast.LENGTH_SHORT)
                        .show()
                },
                modifier = Modifier.padding(16.dp)
            )
        }

        item {
            Box(
                modifier = modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (quotesHasDataUiState.isPaginationLoading) {
                    MyCircularProgressBar(
                        modifier = Modifier.padding(
                            top = 8.dp,
                            bottom = 50.dp
                        )
                    )
                }
                if (quotesHasDataUiState.hasPaginationError) {
                    RetryButton(modifier = Modifier.padding(bottom = 50.dp)) { onLoadNextPage() }
                }
            }
        }
    }
}

@Composable
private fun hasScrolledToEnd(index: Int, quotesHasDataUiState: QuotesUiState.HasData) =
    index >= quotesHasDataUiState.quotesList.size - 1 &&
        quotesHasDataUiState.hasReachedEnd.not() &&
        quotesHasDataUiState.isPaginationLoading.not() &&
        quotesHasDataUiState.hasPaginationError.not()

@Composable
private fun EmptyQuotesView() {
    Image(
        painter = painterResource(id = R.drawable.ic_empty_quotes),
        contentDescription = null,
        modifier = Modifier.size(300.dp)
    )
}

@Composable
private fun RetryButton(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        shape = RoundedCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = MyApplicationTheme.colors.secondary)
    ) {
        Text(
            text = stringResource(id = R.string.btn_quotes_loading_retry),
            style = MaterialTheme.typography.bodySmall,
            color = MyApplicationTheme.colors.onSecondary
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RetryButtonPreview() {
    MyApplicationTheme {
        RetryButton {}
    }
}

@Preview(showBackground = true)
@Composable
private fun QuotesScreenPreview() {
    MyApplicationTheme {
        QuotesScreen(uiState = QuotesUiState.HasData(), {})
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyQuotesPreview() {
    MyApplicationTheme {
        QuotesScreen(uiState = QuotesUiState.Empty, {})
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingQuotesPreview() {
    MyApplicationTheme {
        QuotesScreen(uiState = QuotesUiState.Loading, {})
    }
}
