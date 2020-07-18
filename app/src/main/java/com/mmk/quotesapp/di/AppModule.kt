package com.mmk.quotesapp.di

import android.app.Application
import com.mmk.quotesapp.R
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class AppModule {
    companion object{

        @Singleton
        @Provides
        fun providePicasso(application:Application)=Picasso.with(application)

    }
}