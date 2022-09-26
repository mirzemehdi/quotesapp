package com.mmk.quotes.presentation.allquotes

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ItemLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ItemLoadingViewHolder>() {

    override fun onBindViewHolder(holder: ItemLoadingViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ItemLoadingViewHolder {
        return ItemLoadingViewHolder.from(parent, retry)
    }
}
