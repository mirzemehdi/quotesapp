package com.mmk.quotesapp.di

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.mmk.quotesapp.model.PictureData
import com.mmk.quotesapp.network.PhotoService
import com.mmk.quotesapp.repository.PicturePagingSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object PicturePager {

    @Qualifier
    @Retention(AnnotationRetention.RUNTIME)
    annotation class PagerPicture


    @Singleton
    @Provides
    fun providePicturePagingSourceFactory(apiService: PhotoService): () -> PicturePagingSource {
        return { PicturePagingSource(apiService) }
    }

    @Singleton
    @Provides
    fun providePageConfig(): PagingConfig {
        return PagingConfig(pageSize = 10, enablePlaceholders = false)
    }

    @Singleton
    @Provides
    @PagerPicture
    fun providePicturePager(
        pageConfig: PagingConfig,
        pagingSourceFactory: () -> PicturePagingSource
    ): Pager<Int, PictureData> {
        return Pager(config = pageConfig, pagingSourceFactory = pagingSourceFactory)
    }

    @Singleton
    @Provides
    fun provideFirebaseFirestore():FirebaseFirestore{
        return Firebase.firestore
    }


}

