package com.mmk.common.ui.di

import org.koin.core.module.Module
import org.koin.dsl.module

private val commonUiModule = module {
    // Add common ui module implementations here
}
internal expect val platformUiModule: Module

val uiModule: Module get() = module {
    includes(commonUiModule + platformUiModule)
}
