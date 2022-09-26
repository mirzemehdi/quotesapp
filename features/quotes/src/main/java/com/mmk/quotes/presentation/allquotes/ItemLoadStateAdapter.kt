package com.mmk.quotes.presentation.allquotes

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class ItemLoadStateAdapter(private val retry: () -> Unit) : LoadStateAdapter<ItemLoadingVH>() {

    override fun onBindViewHolder(holder: ItemLoadingVH, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): ItemLoadingVH {
        return ItemLoadingVH.from(parent, retry)
    }
}
