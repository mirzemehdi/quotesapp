package com.mmk.root

import com.mmk.core.util.logger.AppLogger
import com.mmk.root.di.appModules
import org.koin.core.KoinApplication
import org.koin.core.context.startKoin

object AppInitializer {

    fun initialize(isDebug: Boolean = true, onKoinStart: KoinApplication.() -> Unit) {
        if (isDebug) initializeForDebugBuilds()
        startKoin {
            onKoinStart()
            modules(appModules)
        }
    }

    private fun initializeForDebugBuilds() {
        AppLogger.initialize()
    }
}
