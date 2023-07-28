package com.mmk

import com.mmk.di.appModule
import io.ktor.server.application.*
import io.ktor.server.netty.*
import com.mmk.plugins.*
import io.ktor.server.resources.*
import org.koin.ktor.plugin.Koin

fun main(args: Array<String>): Unit = EngineMain.main(args)

@Suppress("unused") // application.conf references the main function.
fun Application.module() {
    install(Koin) {
        modules(appModule)
    }
    install(Resources)
    configureSockets()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
