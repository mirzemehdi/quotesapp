package com.mmk.quotesapp.ui.base

import android.app.Application
import com.mmk.data.di.networkModule
import com.mmk.data.di.repositoryModule
import com.mmk.domain.di.interactionModule
import com.mmk.quotesapp.BuildConfig
import com.mmk.quotesapp.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@BaseApplication)
            modules(domainModules + dataModules + appModules)
        }
    }

    val domainModules = listOf(interactionModule)
    val dataModules = listOf(networkModule, repositoryModule)
    val appModules = listOf(viewModelModule)
}