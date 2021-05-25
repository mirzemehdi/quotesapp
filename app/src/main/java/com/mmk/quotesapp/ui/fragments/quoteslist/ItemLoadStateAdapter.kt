package com.mmk.quotesapp.ui.fragments.quoteslist

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import com.mmk.quotesapp.ui.fragments.quoteslist.ItemLoadingViewHolder

class ItemLoadStateAdapter(private val retry: () -> Unit) :
    LoadStateAdapter<ItemLoadingViewHolder>() {
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


