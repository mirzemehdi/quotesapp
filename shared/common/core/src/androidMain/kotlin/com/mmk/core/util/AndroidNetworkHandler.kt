package com.mmk.core.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.getSystemService

@Suppress("FunctionOnlyReturningConstant")
class AndroidNetworkHandler(private val context: Context) : NetworkHandler {
    override fun hasNetworkConnection(): Boolean {
        val connectivityManager = getSystemService(context, ConnectivityManager::class.java)
            ?: return false

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkNetworkConnectionAboveSdk23(connectivityManager)
        } else {
            checkNetworkConnectionBelowSdk23(connectivityManager)
        }
    }

    @Suppress("DEPRECATION")
    private fun checkNetworkConnectionBelowSdk23(connectivityManager: ConnectivityManager) =
        connectivityManager.activeNetworkInfo?.isConnected ?: false

    @RequiresApi(Build.VERSION_CODES.M)
    private fun checkNetworkConnectionAboveSdk23(connectivityManager: ConnectivityManager): Boolean {
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        return networkCapabilities?.let {
            when {
                it.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                it.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                it.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                else -> false
            }
        } ?: false
    }
}
