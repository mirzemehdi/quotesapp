package com.mmk.quotesapp.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mmk.quotesapp.model.PictureData
import com.mmk.quotesapp.network.PhotoService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideFirebaseFirestore():FirebaseFirestore{
        return Firebase.firestore
    }


}

