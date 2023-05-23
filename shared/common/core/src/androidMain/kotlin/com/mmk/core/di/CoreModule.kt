package com.mmk.core.di

import com.mmk.core.util.AndroidNetworkHandler
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

internal actual val platformCoreModule: Module = module {
    singleOf(::AndroidNetworkHandler)
}
