package com.mmk.quotes.data.mappers

import com.mmk.core.mapper.NetworkMapper
import com.mmk.quotes.data.source.remote.model.request.NewQuoteRequest
import com.mmk.quotes.domain.model.Quote

class QuoteMapper : NetworkMapper<Quote, NewQuoteRequest> {
    override fun mapToNetworkRequest(domainObj: Quote): NewQuoteRequest {
        return with(domainObj) {
            NewQuoteRequest(this.id, this.author, this.text, timeStamp = domainObj.timeStamp)
        }
    }
}
