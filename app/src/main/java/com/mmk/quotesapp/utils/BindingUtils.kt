package com.mmk.quotesapp.utils

import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
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

@BindingAdapter("loadingState")
fun ProgressBar.loadingState(uiState: UiState){
    this.visibility=when(uiState){
        UiState.Loading ->View.VISIBLE
        else -> View.GONE
    }
}