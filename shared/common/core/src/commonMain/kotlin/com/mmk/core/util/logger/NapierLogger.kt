package com.mmk.core.util.logger

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier

class NapierLogger : Logger {
    override fun initialize() {
        Napier.base(DebugAntilog())
    }

    override fun e(message: String?) {
        Napier.e(message ?: "")
    }

    override fun d(message: String?) {
        Napier.d(message ?: "")
    }

    override fun i(message: String?) {
        Napier.i(message ?: "")
    }
}
