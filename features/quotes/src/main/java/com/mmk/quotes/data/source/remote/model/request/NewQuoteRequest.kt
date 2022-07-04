package com.mmk.quotes.data.source.remote.model.request

data class NewQuoteRequest(
    var id: String,
    val author: String = "",
    val text: String = ""
)
