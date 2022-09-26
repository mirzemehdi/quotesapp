package com.mmk.quotes.presentation.allquotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.mmk.quotes.databinding.ItemQuoteLoadStateBinding

class ItemLoadingVH private constructor(
    private val binding: ItemQuoteLoadStateBinding,
    private val retry: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(loadState: LoadState) {
        binding.apply {
            val isLoading = loadState is LoadState.Loading
            progressBar.isVisible = isLoading
            buttonRetry.isVisible = isLoading.not()
            buttonRetry.setOnClickListener { retry.invoke() }
        }
    }

    companion object {
        fun from(parent: ViewGroup, retry: () -> Unit): ItemLoadingVH {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ItemQuoteLoadStateBinding.inflate(inflater, parent, false)
            return ItemLoadingVH(binding, retry)
        }
    }
}
