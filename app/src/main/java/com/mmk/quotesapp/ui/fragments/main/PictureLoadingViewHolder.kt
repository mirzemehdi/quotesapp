package com.mmk.quotesapp.ui.fragments.main

import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import com.mmk.quotesapp.databinding.ItemLoadStateBinding
import com.mmk.quotesapp.databinding.ItemPictureListBinding
import com.mmk.quotesapp.model.PictureData
import com.mmk.quotesapp.utils.layoutInflater


class PictureLoadingViewHolder private constructor(private val binding: ItemLoadStateBinding,
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
        fun from(parent: ViewGroup,retry:()->Unit): PictureLoadingViewHolder {
            val inflater = parent.context.layoutInflater
            val binding = ItemLoadStateBinding.inflate(inflater, parent, false)
            return PictureLoadingViewHolder(binding,retry)
        }
    }
}