package com.mmk.quotesapp.ui.fragments.main

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter

class PictureLoadStateAdapter(private val retry:()->Unit) : LoadStateAdapter<PictureLoadingViewHolder>() {
    override fun onBindViewHolder(holder: PictureLoadingViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): PictureLoadingViewHolder {
        return PictureLoadingViewHolder.from(parent,retry)
    }
}