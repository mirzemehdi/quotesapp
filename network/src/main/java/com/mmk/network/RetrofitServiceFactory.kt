package com.mmk.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class ApiServiceFactory(baseUrl: String) {

    val retrofit: Retrofit by lazy {
        Retrofit.Builder().client(provideOkHttpClient(provideHttpLogging()))
            .baseUrl(baseUrl)
            .addConverterFactory(provideConverterFactory())
            .build()
    }


    

    inline fun <reified T> createApiService(): T = retrofit.create(T::class.java)

    private fun provideHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    private fun provideOkHttpClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
        val builder =
            OkHttpClient.Builder()
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)

        val isLoggingEnabled = true
        if (isLoggingEnabled) builder.addInterceptor(interceptor)
        return builder.build()
    }

    private fun provideConverterFactory(): Converter.Factory {
        val moshi = Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()

        return MoshiConverterFactory.create(moshi)
    }


}