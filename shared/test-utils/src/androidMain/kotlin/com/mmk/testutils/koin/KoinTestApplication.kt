package com.mmk.testutils.koin

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class KoinTestApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@KoinTestApplication)
            modules(emptyList())
        }
    }

    fun injectModule(module: Module) {
        loadKoinModules(module)
    }
}
