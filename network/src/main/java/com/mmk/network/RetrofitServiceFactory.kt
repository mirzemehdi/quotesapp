package com.mmk.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitServiceFactory {

    private val retrofits: MutableMap<String, Retrofit> = mutableMapOf()


    fun getInstance(
        baseUrl: String,
        readWriteTimeOutInSeconds: Long = 10L,
        isLoggingEnabled: Boolean = BuildConfig.DEBUG
    ): Retrofit = synchronized(this) {
        retrofits[baseUrl] ?: Retrofit.Builder()
            .client(
                provideOkHttpClient(
                    interceptor = provideHttpLogging(),
                    readWriteTimeOutInSeconds = readWriteTimeOutInSeconds,
                    isLoggingEnabled = isLoggingEnabled
                )
            )
            .baseUrl(baseUrl)
            .addConverterFactory(provideConverterFactory())
            .build().also {
                retrofits[baseUrl] = it
            }
    }

    inline fun <reified T> Retrofit.createApiService(): T = this.create(T::class.java)


    private fun provideHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }


    private fun provideOkHttpClient(
        interceptor: HttpLoggingInterceptor,
        readWriteTimeOutInSeconds: Long,
        isLoggingEnabled: Boolean
    ): OkHttpClient {

        val builder = OkHttpClient.Builder()
            .writeTimeout(readWriteTimeOutInSeconds, TimeUnit.SECONDS)
            .readTimeout(readWriteTimeOutInSeconds, TimeUnit.SECONDS)

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
