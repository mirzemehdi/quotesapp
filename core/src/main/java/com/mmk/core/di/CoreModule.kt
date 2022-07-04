package com.mmk.core.di

import com.mmk.core.util.NetworkHandler
import org.koin.dsl.module

val coreModule = module {
    single<NetworkHandler> { NetworkHandler() }
}
