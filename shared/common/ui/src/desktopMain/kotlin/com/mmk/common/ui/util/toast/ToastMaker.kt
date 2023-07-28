package com.mmk.common.ui.util.toast

import org.koin.core.component.KoinComponent
import org.koin.core.component.get

actual object ToastMaker : KoinComponent {
    actual fun get(): ToastMessageHandler = (this as KoinComponent).get()
}
