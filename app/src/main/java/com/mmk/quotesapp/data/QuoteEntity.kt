package com.mmk.quotesapp.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class QuoteEntity( ) {
    @PrimaryKey(autoGenerate = true) var id:Int?=null
}