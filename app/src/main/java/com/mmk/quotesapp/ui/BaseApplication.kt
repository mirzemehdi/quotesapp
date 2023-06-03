package com.mmk.quotesapp.ui

import android.app.Application
import com.mmk.root.AppInitializer
import org.koin.android.ext.koin.androidContext

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        AppInitializer.initialize(isDebug = true) {
            androidContext(this@BaseApplication)
        }
    }
}
