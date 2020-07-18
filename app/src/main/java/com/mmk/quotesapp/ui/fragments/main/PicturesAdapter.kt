package com.mmk.quotesapp.ui.fragments.main

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.mmk.quotesapp.data.PictureData
import javax.inject.Inject

class PicturesAdapter @Inject constructor() : ListAdapter<PictureData, PictureViewHolder>(PictureDiffCallBack()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
       return PictureViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        val item=getItem(position)
        item?.let { holder.bind(it) }
    }

    private class PictureDiffCallBack: DiffUtil.ItemCallback<PictureData>() {
        override fun areItemsTheSame(oldItem: PictureData, newItem: PictureData): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: PictureData, newItem: PictureData): Boolean {
            return oldItem==newItem
        }
    }
}