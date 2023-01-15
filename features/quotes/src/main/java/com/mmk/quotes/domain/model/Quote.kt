package com.mmk.quotes.domain.model

import java.util.UUID

data class Quote(
    var id: String = UUID.randomUUID().toString(),
    val author: String = "",
    val text: String = "",
    val isLiked: Boolean = false
)
