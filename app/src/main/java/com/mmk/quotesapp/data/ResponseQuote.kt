package com.mmk.quotesapp.data

import com.squareup.moshi.Json

data class ResponseQuote(
    @Json(name = "contents") val contents: Contents

)
data class Contents (

    @Json(name = "quotes") val quotes : List<Quotes>
)

data class Quotes (

    @Json(name = "quote") val quote : String,
    @Json(name="length") val length : Int,
    @Json(name="author") val author : String,
    @Json(name="tags") val tags : List<String>,
    @Json(name="category") val category : String,
    @Json(name="language") val language : String,
    @Json(name="date") val date : String,
    @Json(name="permalink") val permalink : String,
    @Json(name="id") val id : String,
    @Json(name="background") val background : String,
    @Json(name="title") val title : String
)