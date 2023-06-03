package com.mmk.core.util

import kotlinx.cinterop.*
import platform.SystemConfiguration.*
import platform.posix.AF_INET
import platform.posix.sockaddr_in

internal class IosNetworkHandler : NetworkHandler {
    override fun hasNetworkConnection(): Boolean {
        memScoped {
            val zeroAddress = alloc<sockaddr_in>()
            zeroAddress.sin_len = sizeOf<sockaddr_in>().toUByte()
            zeroAddress.sin_family = AF_INET.toUByte()

            val defaultRouteReachability =
                SCNetworkReachabilityCreateWithAddress(null, zeroAddress.ptr.reinterpret())
                    ?: return false

            var flags = allocArray<SCNetworkReachabilityFlagsVar>(10)

            if (!SCNetworkReachabilityGetFlags(defaultRouteReachability, flags)) {
                return false
            }

            val results = (0..9).map { flags[it] }.toTypedArray()

            val isReachable = results.contains(kSCNetworkFlagsReachable)
            val needsConnection = results.contains(kSCNetworkFlagsConnectionRequired)

            return isReachable && !needsConnection
        }
    }
}
