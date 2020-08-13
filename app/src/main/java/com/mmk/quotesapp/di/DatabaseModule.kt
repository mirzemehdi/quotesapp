package com.mmk.quotesapp.di

import android.app.Application
import androidx.room.Room
import com.mmk.quotesapp.db.PicturesDB
import com.mmk.quotesapp.db.PicturesDao
import com.mmk.quotesapp.utils.DB_NAME_PICTURES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object DatabaseModule {


        @Provides
        @Singleton
        fun providePicturesDB(application: Application): PicturesDB {
            return Room
                .databaseBuilder(application, PicturesDB::class.java, DB_NAME_PICTURES)
                .fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        @Singleton
        fun providePicturesDao(picturesDB: PicturesDB): PicturesDao {
            return picturesDB.picturesDao()
        }

}