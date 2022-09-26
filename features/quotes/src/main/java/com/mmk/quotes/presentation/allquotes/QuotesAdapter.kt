package com.mmk.quotes.presentation.allquotes

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.mmk.quotes.domain.model.Quote

/**
 * Created by mirzemehdi on 8/12/20
 */
class QuotesAdapter :
    PagingDataAdapter<Quote, QuotesVH>(QuoteItemDiffCallBack()) {

    var onClickLikeButton: ((quote: Quote) -> Unit) = {}
    var onClickQuoteItem: ((quote: Quote) -> Unit) = {}

    override fun onBindViewHolder(holder: QuotesVH, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesVH {
        return QuotesVH.from(parent, onClickQuoteItem, onClickLikeButton)
    }

    private class QuoteItemDiffCallBack : DiffUtil.ItemCallback<Quote>() {
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem == newItem
        }
    }
}
