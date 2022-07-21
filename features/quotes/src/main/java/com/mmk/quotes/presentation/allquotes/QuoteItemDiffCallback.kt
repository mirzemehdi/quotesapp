package com.mmk.quotes.presentation.allquotes

import androidx.recyclerview.widget.DiffUtil
import com.mmk.quotes.domain.model.Quote

class QuoteItemDiffCallback : DiffUtil.ItemCallback<Quote>() {
    override fun areItemsTheSame(oldItem: Quote, newItem: Quote) = oldItem.id == newItem.id
    override fun areContentsTheSame(oldItem: Quote, newItem: Quote) = oldItem == newItem
}
