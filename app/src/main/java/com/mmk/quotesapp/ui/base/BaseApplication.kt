package com.mmk.quotesapp.ui.base

import android.app.Application
import com.mmk.core.util.logger.AppLogger
import com.mmk.quotesapp.BuildConfig
import com.mmk.quotesapp.di.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            AppLogger.initialize()

        startKoin {
//            androidLogger()
            androidContext(this@BaseApplication)
            modules(appModules)
        }
    }
}
