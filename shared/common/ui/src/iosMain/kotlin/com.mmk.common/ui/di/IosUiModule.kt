package com.mmk.common.ui.di

import com.mmk.common.ui.util.toast.IosToastMessageHandler
import com.mmk.common.ui.util.toast.ToastMessageHandler
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal actual val platformUiModule: Module = module {
    factoryOf(::IosToastMessageHandler) bind ToastMessageHandler::class
}