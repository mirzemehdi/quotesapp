package com.mmk.quotesapp.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mmk.quotesapp.data.PictureData
import com.mmk.quotesapp.data.PictureEntity

@Database(entities = [PictureEntity::class],version = 1,exportSchema = false)
abstract class PicturesDB:RoomDatabase() {
    abstract fun picturesDao():PicturesDao
}