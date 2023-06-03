package com.mmk.quotesapp.ui.base

import android.app.Application
import com.mmk.quotesapp.BuildConfig
import com.mmk.quotesapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())

        startKoin {
//            androidLogger()
            androidContext(this@BaseApplication)
            modules(appModules)
        }
    }
}
