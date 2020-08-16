package com.mmk.quotesapp.utils

import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.mmk.quotesapp.BR

/**
 * Created by mirzemehdi on 8/11/20
 */

interface GenericRecyclerViewItemModel

class GenericRecyclerViewHolder<T : GenericRecyclerViewItemModel> private constructor(
    private val binding: ViewDataBinding,
    private val onClickItem: ((item: T) -> Unit)?
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
        binding.apply {
            setVariable(BR.listItem, item)
            executePendingBindings()
        }
        itemView.setOnClickListener { onClickItem?.invoke(item) }
    }

    companion object {
        fun <T : GenericRecyclerViewItemModel> from(
            parent: ViewGroup,
            @LayoutRes layoutId: Int,
            onClickItem: ((item: T) -> Unit)?
        ): GenericRecyclerViewHolder<T> {
            val inflater = parent.context.layoutInflater
            val binding =
                DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, parent, false)
            return GenericRecyclerViewHolder(binding,onClickItem)
        }
    }
}





open class GenericRecyclerViewAdapter<T : GenericRecyclerViewItemModel>(
    @LayoutRes val layoutId: Int,
    diffCallback: DiffUtil.ItemCallback<T>
) :
    PagingDataAdapter<T, GenericRecyclerViewHolder<T>>(diffCallback) {

    open var onClickItem:((item:T)->Unit )?=null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericRecyclerViewHolder<T> {

        return GenericRecyclerViewHolder.from(parent, layoutId,onClickItem)

    }

    override fun onBindViewHolder(holder: GenericRecyclerViewHolder<T>, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }


    }


}




