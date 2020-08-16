package com.mmk.quotesapp.utils

import android.view.LayoutInflater
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

class GenericRecyclerViewHolder<T> private constructor(
    private val binding: ViewDataBinding,
    private val onClickItem: ((item: T) -> Unit)?,
    private val onBinding: ((item: T,binding:ViewDataBinding) -> Unit)?
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: T) {
        binding.apply {
            setVariable(BR.listItem, item)
            onBinding?.invoke(item,binding)
            executePendingBindings()
        }

        itemView.setOnClickListener { onClickItem?.invoke(item) }
    }

    companion object {
        fun <T > from(
            parent: ViewGroup,
            @LayoutRes layoutId: Int,
            onClickItem: ((item: T) -> Unit)?,
            onBinding: ((item: T,binding:ViewDataBinding) -> Unit)?
        ): GenericRecyclerViewHolder<T> {
            val inflater = LayoutInflater.from(parent.context)
            val binding =
                DataBindingUtil.inflate<ViewDataBinding>(inflater, layoutId, parent, false)
            return GenericRecyclerViewHolder(binding,onClickItem,onBinding)
        }
    }
}





open class GenericRecyclerViewAdapter<T:Any>(
    @LayoutRes val layoutId: Int,
    diffCallback: DiffUtil.ItemCallback<T>
) :
    PagingDataAdapter<T, GenericRecyclerViewHolder<T>>(diffCallback) {

    open var onClickItem:((item:T)->Unit )?=null
    protected open fun onBinding (item: T,binding:ViewDataBinding)=Unit

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GenericRecyclerViewHolder<T> {

        return GenericRecyclerViewHolder.from(parent, layoutId,onClickItem,::onBinding)

    }

    override fun onBindViewHolder(holder: GenericRecyclerViewHolder<T>, position: Int) {
        val item = getItem(position)
        item?.let { holder.bind(it) }
    }


}




