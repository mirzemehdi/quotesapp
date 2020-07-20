package com.mmk.quotesapp.utils


import android.content.Context
import android.view.LayoutInflater
import com.mmk.quotesapp.model.PictureData
import com.mmk.quotesapp.model.ResponsePictureResult


val Context.layoutInflater:LayoutInflater
    get()=LayoutInflater.from(this)

fun List<ResponsePictureResult>.toPictureDataList(): List<PictureData> {
    return this.map {
        with(it) {
            PictureData(id, urls?.small, urls?.regular)
        }
    }
}