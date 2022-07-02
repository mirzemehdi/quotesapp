package com.mmk.quotes.domain.model

data class Quote(
    var id: String? = null,
    val author: String = "",
    val text: String = "",
    val isLiked: Boolean = false
)
