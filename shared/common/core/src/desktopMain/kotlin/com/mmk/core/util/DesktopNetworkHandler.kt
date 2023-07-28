package com.mmk.core.util

import java.net.InetAddress

internal class DesktopNetworkHandler : NetworkHandler {
    override fun hasNetworkConnection(): Boolean {
        return try {
            val address = InetAddress.getByName("www.google.com")
            address != null && !address.equals("")
        } catch (e: Exception) {
            false
        }
    }
}
