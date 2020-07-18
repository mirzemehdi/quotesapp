package com.mmk.quotesapp.utils


import android.content.Context
import android.view.LayoutInflater
import com.mmk.quotesapp.data.PictureData
import com.mmk.quotesapp.data.ResponsePictureResult


val Context.layoutInflater:LayoutInflater
    get()=LayoutInflater.from(this)

fun List<ResponsePictureResult>.toPictureDataList(): List<PictureData> {
    return this.map {
        with(it) {
            PictureData(id, urls?.small, urls?.regular)
        }
    }
}