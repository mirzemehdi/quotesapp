package com.mmk.quotesapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.mmk.quotesapp.db.QuotesDB
import com.mmk.quotesapp.db.QuotesDao
import com.mmk.quotesapp.utils.DB_NAME_QUOTES
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class DatabaseModule {

    companion object {

        @Provides
        @Singleton
        fun provideQuotesDB(application: Application): QuotesDB {
            return Room
                .databaseBuilder(application, QuotesDB::class.java, DB_NAME_QUOTES)
                .fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        @Singleton
        fun provideQuotesDao(quotesDB: QuotesDB): QuotesDao {
            return quotesDB.quotesDao()
        }
    }
}