package com.mmk.quotesapp.utils


import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.mmk.quotesapp.model.PictureData
import com.mmk.quotesapp.model.ResponsePictureResult


val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Context.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    text?.let {
        Toast.makeText(this, it, duration).show()
    }

}

fun Fragment.toast(text: String?, duration: Int = Toast.LENGTH_SHORT){
    context?.toast(text,duration)
}




fun List<ResponsePictureResult>.toPictureDataList(): List<PictureData> {
    return this.map {
        with(it) {
            PictureData(id, urls?.small, urls?.regular)
        }
    }
}