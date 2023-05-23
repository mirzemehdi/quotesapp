package com.mmk.core.di

import org.koin.core.module.Module
import org.koin.dsl.module

private val commonCoreModule = module {
    // Add common core module implementations here
}
internal expect val platformCoreModule: Module

// https://github.com/InsertKoinIO/koin/issues/1341
// writing get() is important
val coreModule: Module get() = module {
    includes(commonCoreModule + platformCoreModule)
}
