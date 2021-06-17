package com.mmk.quotesapp.ui.fragments.quoteslist

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import com.mmk.domain.model.Quote
import com.mmk.quotesapp.R
import com.mmk.quotesapp.databinding.ItemQuoteListBinding
import com.mmk.quotesapp.utils.GenericRecyclerViewAdapter

/**
 * Created by mirzemehdi on 8/12/20
 */
class QuotesAdapter :
    GenericRecyclerViewAdapter<Quote>(R.layout.item_quote_list, QuoteItemDiffCallBack()) {

    var onLikeButtonClicked: ((quote: Quote) -> Unit) = {}

    override fun onBinding(item: Quote, binding: ViewDataBinding) {
        binding as ItemQuoteListBinding
        binding.quoteLikeImageView.setOnClickListener {
            onLikeButtonClicked.invoke(item)
        }
        super.onBinding(item, binding)
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