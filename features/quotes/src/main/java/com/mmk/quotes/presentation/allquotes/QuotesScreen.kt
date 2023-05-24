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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.mmk.common.ui.components.MyCircularProgressBar
import com.mmk.common.ui.components.UiMessageHolderComponent
import com.mmk.common.ui.theme.MyApplicationTheme
import com.mmk.quotes.R
import com.mmk.quotes.domain.model.Quote
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuotesScreen(viewModel: QuotesVM = koinViewModel()) {
    UiMessageHolderComponent(uiMessageHolder = viewModel) {
        val lazyPagingQuoteItems = viewModel.quotesListFlow.collectAsLazyPagingItems()
        val uiState by viewModel.getQuotesUiState.collectAsStateWithLifecycle()

        val loadState = lazyPagingQuoteItems.loadState
        LaunchedEffect(key1 = loadState.refresh) {
            viewModel.onPageAdapterLoadingStateChanged(
                loadState = loadState.refresh,
                lazyPagingQuoteItems.itemCount
            )
        }
        QuotesScreen(uiState = uiState, quotes = lazyPagingQuoteItems)
    }
}

@Composable
private fun QuotesScreen(uiState: QuotesUiState, quotes: LazyPagingItems<Quote>) {
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
                QuotesUiState.HasData -> QuotesDataList(
                    quotes = quotes,
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
fun QuotesDataList(quotes: LazyPagingItems<Quote>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(0.dp)) {
        items(quotes) {
            it?.let {
                val context = LocalContext.current
                QuoteItem(
                    quote = it,
                    onClickItem = {
                        Toast.makeText(context, it.text, Toast.LENGTH_SHORT).show()
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
        }
        item {
            Box(
                modifier = modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                if (quotes.loadState.append is LoadState.Loading) {
                    MyCircularProgressBar(
                        modifier = Modifier.padding(
                            top = 8.dp,
                            bottom = 50.dp
                        )
                    )
                }
                if (quotes.loadState.append is LoadState.Error) {
                    RetryButton(modifier = Modifier.padding(bottom = 50.dp)) { quotes.retry() }
                }
            }
        }
    }
}

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
        colors = ButtonDefaults.buttonColors(
            containerColor = MyApplicationTheme.colors.secondary,
            contentColor = MyApplicationTheme.colors.onPrimary
        )
    ) {
        Text(
            text = stringResource(id = R.string.btn_quotes_loading_retry),
            style = MaterialTheme.typography.bodySmall
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
        QuotesScreen(
            uiState = QuotesUiState.HasData,
            quotes = getTestQuotes().collectAsLazyPagingItems()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun EmptyQuotesPreview() {
    MyApplicationTheme {
        QuotesScreen(
            uiState = QuotesUiState.Empty,
            quotes = getTestQuotes().collectAsLazyPagingItems()
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun LoadingQuotesPreview() {
    MyApplicationTheme {
        QuotesScreen(
            uiState = QuotesUiState.Loading,
            quotes = getTestQuotes().collectAsLazyPagingItems()
        )
    }
}

@Suppress("MagicNumber")
private fun getTestQuotes(): Flow<PagingData<Quote>> {
    val list = MutableList(20) { Quote(author = "TestAuthor", text = "TestText") }
    val pagingData = PagingData.from(list)
    return flowOf(pagingData)
}
