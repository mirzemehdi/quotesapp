package com.mmk.quotesapp.ui.fragments.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.RecyclerView
import com.mmk.quotesapp.data.QuoteData
import com.mmk.quotesapp.data.QuoteEntity
import com.mmk.quotesapp.databinding.ItemQuoteListBinding

class QuotesViewHolder(private val binding: ItemQuoteListBinding):RecyclerView.ViewHolder(binding.root) {

    fun bind(quoteEntity: QuoteData){
        binding.apply {
            item=quoteEntity
            executePendingBindings()
        }
    }


companion object {
    fun from(parent: ViewGroup): QuotesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemQuoteListBinding.inflate(inflater, parent, false)
        return QuotesViewHolder(binding)
    }
}
}