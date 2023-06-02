package com.mmk.quotes.presentation.allquotes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mmk.common.ui.MR
import com.mmk.common.ui.theme.getColors
import com.mmk.quotes.domain.model.Quote
import dev.icerock.moko.resources.compose.painterResource

@Composable
fun QuoteItem(
    quote: Quote,
    modifier: Modifier = Modifier,
    onClickItem: () -> Unit = {},
    onClickLikeButton: () -> Unit = {},
    onClickShareButton: () -> Unit = {}

) {
    ElevatedCard(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = getColors().surface,
            contentColor = getColors().onSurface
        )
    ) {
        Column(
            modifier = Modifier
                .background(getColors().surface, RoundedCornerShape(10.dp))
                .clickable { onClickItem() }
                .padding(20.dp)
        ) {
            Icon(
                painter = painterResource(MR.images.ic_quote),
                contentDescription = null,
                modifier = Modifier.size(36.dp)
            )
            Text(
                text = quote.text,
                modifier = Modifier.padding(start = 44.dp, top = 24.dp, end = 4.dp),
                color = getColors().onSurface,
                style = MaterialTheme.typography.bodyMedium.copy(fontSize = 20.sp)
            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(start = 44.dp, top = 20.dp)
            ) {
                Box(
                    modifier = Modifier
                        .width(16.dp)
                        .height(1.dp)
                        .background(getColors().onSurface)
                )
                Text(
                    text = quote.author,
                    modifier = Modifier
                        .weight(1f)
                        .padding(start = 8.dp),
                    color = getColors().inActive,
                    style = MaterialTheme.typography.bodySmall.copy(fontSize = 16.sp)
                )
                val likeIconColor = if (quote.isLiked) Color.Red else LocalContentColor.current
                val likeIcon =
                    if (quote.isLiked) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder

                IconButton(
                    onClick = { onClickLikeButton() },
                    modifier = Modifier
                        .padding(16.dp)
                        .size(24.dp)
                ) {
                    Icon(
                        imageVector = likeIcon, contentDescription = null, tint = likeIconColor
                    )
                }

                IconButton(
                    onClick = { onClickShareButton() }, modifier = Modifier.size(24.dp)
                ) {
                    Icon(imageVector = Icons.Filled.Share, contentDescription = null)
                }
            }
        }
    }
}

// TODO search how to do preview
// @Preview(showBackground = true)
// @Composable
// private fun QuoteItemPreview() {
//    MyApplicationTheme {
//        val quote = Quote(
//            author = stringResource(MR.strings.sample_author),
//            text = stringResource(MR.strings.sample_quote),
//            isLiked = false
//        )
//
//        QuoteItem(quote = quote, modifier = Modifier.padding(10.dp))
//    }
// }
