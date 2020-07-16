package com.mmk.quotesapp.ui.fragments.main

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mmk.quotesapp.data.QuoteData

class QuotesAdapter() : ListAdapter<QuoteData, QuotesViewHolder>(QuoteDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
       return QuotesViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        val item=getItem(position)
        item?.let { holder.bind(it) }
    }

    private class QuoteDiffCallBack: DiffUtil.ItemCallback<QuoteData>() {
        override fun areItemsTheSame(oldItem: QuoteData, newItem: QuoteData): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: QuoteData, newItem: QuoteData): Boolean {
            return oldItem==newItem
        }
    }
}