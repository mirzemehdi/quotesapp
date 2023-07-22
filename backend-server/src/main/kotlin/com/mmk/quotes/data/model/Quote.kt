package com.mmk.quotes.data.model

import io.ktor.resources.*
import kotlinx.serialization.Serializable
import org.bson.codecs.pojo.annotations.BsonId
import org.bson.types.ObjectId
import java.time.Clock

@Serializable
data class Quote(
    @BsonId val id: String = ObjectId().toString(),
    val author: String = "",
    val text: String = "",
    val timeStamp: Long = System.currentTimeMillis()
)