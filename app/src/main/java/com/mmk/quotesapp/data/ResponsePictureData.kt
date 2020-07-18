package com.mmk.quotesapp.data

import com.squareup.moshi.Json


data class ResponsePictureResult(
    @Json(name = "id") val id: String,
    @Json(name = "urls") val urls: Urls? = null
)

data class Urls(
    @Json(name = "regular") val regular: String? = null,
    @Json(name = "small") val small: String? = null,
    @Json(name = "thumb") val thumb: String? = null
)


