package com.mmk.quotesapp.utils

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.mmk.quotesapp.BR
import com.mmk.quotesapp.R

/**
 * Created by mirzemehdi on 8/11/20
 */

interface GenericRecyclerViewItemModel

class GenericRecyclerViewHolder<T : GenericRecyclerViewItemModel> private constructor(private val binding: ViewDataBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
        binding.apply {
            setVariable(BR.listItem, item)
            executePendingBindings()
        }
    }

    companion object {
        fun <T : GenericRecyclerViewItemModel> from(
            parent: ViewGroup,
            @LayoutRes layoutId: Int
        ): GenericRecyclerViewHolder<T> {
            val inflater = parent.context.layoutInflater
            val binding =
                DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, parent, false)
            return GenericRecyclerViewHolder(binding)
        }
    }
}





open class GenericRecyclerViewAdapter<T : GenericRecyclerViewItemModel>(
    @LayoutRes val layoutId: Int,
    diffCallback: DiffUtil.ItemCallback<T>
) :
    ListAdapter<T, GenericRecyclerViewHolder<T>>(diffCallback) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericRecyclerViewHolder<T> {

        return GenericRecyclerViewHolder.from(parent, layoutId)

    }

    override fun onBindViewHolder(holder: GenericRecyclerViewHolder<T>, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }

    }


}




