package com.mmk.quotesapp.di

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.mmk.quotesapp.BuildConfig
import com.mmk.quotesapp.network.PhotoService
import com.mmk.quotesapp.utils.BASE_URL
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
class NetworkModule {

    companion object {
        @Provides
        @Singleton
        fun provideLogging(): HttpLoggingInterceptor {
            return HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
        }

        @Provides
        @Singleton
        fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
            val builder = OkHttpClient.Builder()
            if (BuildConfig.DEBUG) builder.addInterceptor(interceptor)

            return builder.build()
        }

        @Provides
        @Singleton
        fun provideCoroutineCallAdapter(): CoroutineCallAdapterFactory {
            return CoroutineCallAdapterFactory()
        }


        @Provides
        @Singleton
        fun provideMoshiConvertor(): MoshiConverterFactory {
            val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
            return MoshiConverterFactory.create(moshi)
        }


        @Provides
        @Singleton
        fun provideRetrofit(
            converterFactory: MoshiConverterFactory,
            adapterFactory: CoroutineCallAdapterFactory,
            client: OkHttpClient
        ): Retrofit {
            return Retrofit.Builder()
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(adapterFactory)
                .client(client)
                .baseUrl(BASE_URL)
                .build()
        }

        @Provides
        @Singleton
        fun provideQuoteService(retrofit: Retrofit): PhotoService {
            return retrofit.create(PhotoService::class.java)
        }
    }

}
