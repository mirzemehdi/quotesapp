package com.mmk.quotesapp.ui.fragments.main

import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.mmk.quotesapp.model.PictureData
import com.mmk.quotesapp.databinding.ItemPictureListBinding
import com.mmk.quotesapp.utils.layoutInflater

class PictureViewHolder private constructor(private val binding: ItemPictureListBinding)
    :RecyclerView.ViewHolder(binding.root) {

    fun bind(pictureData: PictureData){
        binding.apply {
            listItem=pictureData
            executePendingBindings()
        }
    }


companion object {
    fun from(parent: ViewGroup): PictureViewHolder {
        val inflater = parent.context.layoutInflater
        val binding = ItemPictureListBinding.inflate(inflater, parent, false)
        return PictureViewHolder(binding)
    }
}
}