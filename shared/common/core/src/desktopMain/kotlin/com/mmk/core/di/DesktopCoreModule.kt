package com.mmk.core.di

import com.mmk.core.util.DesktopNetworkHandler
import com.mmk.core.util.NetworkHandler
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformCoreModule: Module = module {
    singleOf(::DesktopNetworkHandler) bind NetworkHandler::class
}
