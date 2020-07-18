package com.mmk.quotesapp.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.mmk.quotesapp.R
import com.squareup.picasso.Picasso
import javax.inject.Inject

class  BindingUtils {

    companion object {


        @BindingAdapter("imageUrl")
        @JvmStatic fun ImageView.setImageUrl(imageUrl: String?) {
            Picasso.with(this.context)
                .load(imageUrl)
                .placeholder(R.color.colorPlaceHolder)
                .into(this)
        }
    }
}