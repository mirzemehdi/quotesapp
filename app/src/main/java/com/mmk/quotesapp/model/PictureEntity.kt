package com.mmk.quotesapp.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PictureEntity(
    @PrimaryKey val id: String,
    val smallUrl: String? = null,
    val regularUrl: String? = null
)