package com.mmk.quotes.routes

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Clock

@Serializable
@Resource("/quotes")
data class QuoteResources(
    val pageIndex: String? = null,
    val pageLimit: Int = 25
) {

    @Resource("{id}")
    data class Id(private val parent: QuoteResources, val id: String)
}