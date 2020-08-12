package com.mmk.quotesapp.model

import com.mmk.quotesapp.utils.GenericRecyclerViewItemModel


data class PictureData(
    val id: String,
    val smallUrl: String? = null,
    val regularUrl: String? = null
):GenericRecyclerViewItemModel