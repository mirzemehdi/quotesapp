package com.mmk.quotes.data.source.remote.model.request

import kotlinx.datetime.Clock

@kotlinx.serialization.Serializable
data class NewQuoteRequest(
    var id: String,
    val author: String = "",
    val text: String = "",
    val timeStamp: Long = Clock.System.now().toEpochMilliseconds()
)
