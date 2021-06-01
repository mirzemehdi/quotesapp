package com.mmk.quotesapp.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import com.mmk.quotesapp.R
import com.mmk.quotesapp.ui.base.UiState
import com.squareup.picasso.Picasso

object BindingUtils {

    @BindingAdapter("imageUrl")
    @JvmStatic
    fun ImageView.setImageUrl(imageUrl: String?) {
        Picasso.with(this.context)
            .load(imageUrl)
            .placeholder(R.color.colorPlaceHolder)
            .into(this)
    }
    

}

@BindingAdapter("visibility")
fun View.setVisibility(isVisible: Boolean) {
    this.visibility = if (isVisible) View.VISIBLE else View.GONE
}


@BindingAdapter("showOnLoading")
fun View.showOnLoading(uiState: UiState?) {
    this.apply {
        isVisible = when (uiState) {
            UiState.Loading -> true
            else -> false
        }
    }
}


@BindingAdapter("hideOnLoading")
fun View.showOnLoadingFinished(uiState: UiState?) {
    this.apply {
        isVisible = when (uiState) {
            UiState.Loading -> false
            else -> true

        }
    }
}

@BindingAdapter("showOnHasData")
fun View.showOnHasData(uiState: UiState?) {
    this.apply {
        isVisible = when (uiState) {
            UiState.HasData -> true
            else -> false

        }
    }
}


@BindingAdapter("showOnNoData")
fun View.showOnNoData(uiState: UiState?) {
    this.apply {
        isVisible = when (uiState) {
            UiState.NoData -> true
            else -> false
        }
    }
}
