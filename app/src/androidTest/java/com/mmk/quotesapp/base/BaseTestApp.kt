package com.mmk.quotesapp.base

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class BaseTestApp:Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseTestApp)
            modules(emptyList())
        }
    }

    internal fun injectModule(module: Module) {
        loadKoinModules(module)
    }
}