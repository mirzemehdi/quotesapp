package com.mmk.quotesapp.utils

import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mmk.quotesapp.R
import com.squareup.picasso.Picasso
import javax.inject.Inject

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