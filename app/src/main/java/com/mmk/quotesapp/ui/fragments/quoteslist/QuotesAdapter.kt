package com.mmk.quotesapp.ui.fragments.quoteslist

import androidx.recyclerview.widget.DiffUtil
import com.mmk.quotesapp.R
import com.mmk.quotesapp.model.Quote
import com.mmk.quotesapp.utils.GenericRecyclerViewAdapter
import javax.inject.Inject

/**
 * Created by mirzemehdi on 8/12/20
 */
class QuotesAdapter :GenericRecyclerViewAdapter<Quote>(R.layout.item_quote_list,QuoteItemDiffCallBack()) {

    internal class QuoteItemDiffCallBack:DiffUtil.ItemCallback<Quote>(){
        override fun areItemsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Quote, newItem: Quote): Boolean {
            return oldItem==newItem
        }
    }
}