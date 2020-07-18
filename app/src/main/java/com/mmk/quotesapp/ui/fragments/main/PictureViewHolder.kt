package com.mmk.quotesapp.ui.fragments.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmk.quotesapp.data.PictureData
import com.mmk.quotesapp.databinding.ItemPictureListBinding
import com.mmk.quotesapp.utils.layoutInflater

class PictureViewHolder(private val binding: ItemPictureListBinding):RecyclerView.ViewHolder(binding.root) {

    fun bind(pictureData: PictureData){
        binding.apply {
            item=pictureData
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