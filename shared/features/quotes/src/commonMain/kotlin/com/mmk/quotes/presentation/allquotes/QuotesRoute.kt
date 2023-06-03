
package com.mmk.quotes.presentation.allquotes

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mmk.common.ui.MR
import com.mmk.common.ui.components.MyCircularProgressBar
import com.mmk.common.ui.components.UiMessageOwnerComponent
import com.mmk.common.ui.theme.getColors
import com.mmk.common.ui.util.toast.ToastMaker
import com.mmk.core.util.ViewModelProvider
import com.mmk.core.util.asState
import dev.icerock.moko.resources.compose.painterResource
import dev.icerock.moko.resources.compose.stringResource

@Composable
fun QuotesRoute(viewModel: QuotesVM = ViewModelProvider.get()) {
    UiMessageOwnerComponent(uiMessageOwner = viewModel) {
        val uiState by viewModel.getQuotesUiState.asState()
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
            text = stringResource(MR.strings.title_quotes),
            style = MaterialTheme.typography.headlineLarge,
            color = getColors().onBackground,
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
    val scrollState = rememberLazyListState()
    LazyColumn(modifier = modifier, contentPadding = PaddingValues(0.dp), state = scrollState) {

        items(quotesHasDataUiState.quotesList.size) { index ->
            val quoteItem = quotesHasDataUiState.quotesList[index]
            val hasScrollingReachedToEnd = index >= quotesHasDataUiState.quotesList.size - 1
            if (hasScrollingReachedToEnd && quotesHasDataUiState.hasPaginationError.not()) {
                onLoadNextPage()
            }
            QuoteItem(
                quote = quoteItem,
                onClickItem = {
                    ToastMaker.get().showToast(quoteItem.text)
                },
                onClickLikeButton = {
                    ToastMaker.get().showToast("Clicked Like button")
                },
                onClickShareButton = {
                    ToastMaker.get().showToast("Clicked Share button")
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
private fun EmptyQuotesView() {
    Image(
        painter = painterResource(MR.images.ic_empty_quotes),
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
        colors = ButtonDefaults.buttonColors(containerColor = getColors().secondary)
    ) {

        Text(

            text = stringResource(MR.strings.btn_quotes_loading_retry),
            style = MaterialTheme.typography.bodySmall,
            color = getColors().onSecondary
        )
    }
}

// @Preview(showBackground = true)
// @Composable
// private fun RetryButtonPreview() {
//    MyApplicationTheme {
//        RetryButton {}
//    }
// }
//
// @Preview(showBackground = true)
// @Composable
// private fun QuotesScreenPreview() {
//    MyApplicationTheme {
//        QuotesScreen(uiState = QuotesUiState.HasData(), {})
//    }
// }
//
// @Preview(showBackground = true)
// @Composable
// private fun EmptyQuotesPreview() {
//    MyApplicationTheme {
//        QuotesScreen(uiState = QuotesUiState.Empty, {})
//    }
// }
//
// @Preview(showBackground = true)
// @Composable
// private fun LoadingQuotesPreview() {
//    MyApplicationTheme {
//        QuotesScreen(uiState = QuotesUiState.Loading, {})
//    }
// }
