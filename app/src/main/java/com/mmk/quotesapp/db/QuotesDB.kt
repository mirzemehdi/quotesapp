package com.mmk.quotesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmk.quotesapp.data.QuoteEntity

@Database(entities = [QuoteEntity::class],version = 1,exportSchema = false)
abstract class QuotesDB:RoomDatabase() {
    abstract fun quotesDao():QuotesDao
}