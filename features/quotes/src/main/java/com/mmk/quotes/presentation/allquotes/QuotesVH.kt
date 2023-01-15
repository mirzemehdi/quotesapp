package com.mmk.quotes.presentation.allquotes

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mmk.quotes.databinding.ItemQuoteListBinding
import com.mmk.quotes.domain.model.Quote

class QuotesVH(
    private val binding: ItemQuoteListBinding,
    private val onClickItem: ((item: Quote) -> Unit)? = null,
    private val onClickLikeButton: ((item: Quote) -> Unit)? = null
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Quote) {
        binding.apply {
            quoteAuthor.text = item.author
            quoteText.text = item.text
            root.setOnClickListener { onClickItem?.invoke(item) }
            quoteLikeImageView.setOnClickListener { onClickLikeButton?.invoke(item) }
        }
    }

    companion object {
        fun from(
            parent: ViewGroup,
            onClickItem: ((item: Quote) -> Unit)?,
            onClickLikeButton: (quote: Quote) -> Unit
        ): QuotesVH {
            val inflater = LayoutInflater.from(parent.context)
            return QuotesVH(ItemQuoteListBinding.inflate(inflater, parent, false), onClickItem, onClickLikeButton)
        }
    }
}
