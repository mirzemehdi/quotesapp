package com.mmk.quotesapp.data

import androidx.room.PrimaryKey


data class PictureData(
    val id: String,
    val smallUrl: String? = null,
    val regularUrl: String? = null
)