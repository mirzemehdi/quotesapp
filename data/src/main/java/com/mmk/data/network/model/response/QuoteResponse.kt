package com.mmk.data.network.model.response

import com.mmk.data.mapper.DomainMapper
import com.mmk.domain.model.Quote
import java.util.*

data class QuoteResponse(
    var id: String = "",
    var author: String = "",
    var text: String = "",
    var createdDate: Date? = null
) : DomainMapper<Quote> {

    override fun mapToDomainModel() = Quote(id = id, author = author, text = text)
}