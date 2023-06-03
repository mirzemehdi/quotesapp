package com.mmk.quotes.data.source.remote.model.response

import com.mmk.core.mapper.DomainMapper
import com.mmk.quotes.domain.model.Quote
import kotlinx.datetime.Clock

@kotlinx.serialization.Serializable
data class QuoteResponse(
    var id: String = "",
    var author: String = "",
    var text: String = "",
    val timeStamp: Long = Clock.System.now().toEpochMilliseconds()
) : DomainMapper<Quote> {

    override fun mapToDomainModel() = Quote(id = id, author = author, text = text, timeStamp = timeStamp)
}
