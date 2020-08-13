package com.mmk.quotesapp.ui.fragments.quoteslist

import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.mmk.quotesapp.databinding.ItemLoadStateBinding
import com.mmk.quotesapp.utils.layoutInflater


class ItemLoadingViewHolder private constructor(private val binding: ItemLoadStateBinding,
                                                private val retry:()->Unit) :
    RecyclerView.ViewHolder(binding.root) {



    fun bind(loadState: LoadState) {
        binding.apply {
            isLoading= loadState is LoadState.Loading
            buttonRetry.setOnClickListener { retry.invoke() }
            executePendingBindings()

        }
    }


    companion object {
        fun from(parent: ViewGroup,retry:()->Unit): ItemLoadingViewHolder {
            val inflater = parent.context.layoutInflater
            val binding = ItemLoadStateBinding.inflate(inflater, parent, false)
            return ItemLoadingViewHolder(
                binding,
                retry
            )
        }
    }
}