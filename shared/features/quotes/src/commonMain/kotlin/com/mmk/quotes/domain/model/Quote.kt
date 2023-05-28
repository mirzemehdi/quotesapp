package com.mmk.quotes.domain.model

import com.mmk.core.util.randomUUIDString

data class Quote(
    var id: String = randomUUIDString(),
    val author: String = "",
    val text: String = "",
    val isLiked: Boolean = false
)
