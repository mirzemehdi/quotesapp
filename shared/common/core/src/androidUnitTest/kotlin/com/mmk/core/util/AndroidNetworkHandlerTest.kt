package com.mmk.core.util

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockkStatic
import io.mockk.spyk
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Shadows.shadowOf
import org.robolectric.annotation.Config
import org.robolectric.shadows.ShadowNetworkCapabilities

@RunWith(AndroidJUnit4::class)
internal class AndroidNetworkHandlerTest {
    private lateinit var networkHandler: NetworkHandler
    private lateinit var context: Context
    private var connectivityManager: ConnectivityManager? = null
    private lateinit var networkCapabilities: NetworkCapabilities
    private lateinit var networkInfo: NetworkInfo

    @Before
    fun setUp() {
        context = ApplicationProvider.getApplicationContext<Application>()
        networkHandler = AndroidNetworkHandler(context)
        connectivityManager =
            ContextCompat.getSystemService(context, ConnectivityManager::class.java)
        networkCapabilities = ShadowNetworkCapabilities.newInstance()
        networkInfo = spyk<NetworkInfo>()
    }

    @Test
    fun verifyNetworkHandlerIsNotNull() {
        Truth.assertThat(networkHandler).isNotNull()
    }

    @Test
    fun givenNullConnectivityManger_hasNetworkConnection_returnsFalse() {
        mockkStatic(ContextCompat::class)
        every {
            ContextCompat.getSystemService(
                context,
                ConnectivityManager::class.java
            )
        } returns null

        Truth.assertThat(networkHandler.hasNetworkConnection()).isFalse()
    }

    @Test
    @Config(sdk = [23])
    fun givenNoWifiAndNoCellularConnection_hasNetworkConnection_returnsFalse() {
        connectivityManager?.let {
            shadowOf(connectivityManager).setNetworkCapabilities(
                it.activeNetwork,
                networkCapabilities
            )
            Truth.assertThat(networkHandler.hasNetworkConnection()).isFalse()
        }
    }

    @Test
    @Config(sdk = [23])
    fun givenHasWifiAndNoCellularConnection_hasNetworkConnection_returnsTrue() {
        connectivityManager?.let {
            shadowOf(networkCapabilities).addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            shadowOf(connectivityManager).setNetworkCapabilities(
                it.activeNetwork,
                networkCapabilities
            )
            Truth.assertThat(networkHandler.hasNetworkConnection()).isTrue()
        }
    }

    @Test
    @Config(sdk = [23])
    fun givenNoWifiAndHasCellularConnection_hasNetworkConnection_returnsTrue() {
        connectivityManager?.let {
            shadowOf(networkCapabilities).addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            shadowOf(connectivityManager).setNetworkCapabilities(
                it.activeNetwork,
                networkCapabilities
            )
            Truth.assertThat(networkHandler.hasNetworkConnection()).isTrue()
        }
    }

    @Test
    @Config(sdk = [23])
    fun givenEthernetConnection_hasNetworkConnection_returnsTrue() {
        connectivityManager?.let {
            shadowOf(networkCapabilities).addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            shadowOf(connectivityManager).setNetworkCapabilities(
                it.activeNetwork,
                networkCapabilities
            )
            Truth.assertThat(networkHandler.hasNetworkConnection()).isTrue()
        }
    }

    @Test
    @Config(sdk = [21])
    fun verifyNoActiveNetworkSdkVersionBelow23_hasNetworkConnection_returnsFalse() {
        connectivityManager?.let {
            shadowOf(connectivityManager).setActiveNetworkInfo(null)
            Truth.assertThat(networkHandler.hasNetworkConnection()).isFalse()
        }
    }

    @Test
    @Config(sdk = [21])
    fun verifyGivenConnectedNetworkSdkVersionBelow23_hasNetworkConnection_returnsTrue() {
        connectivityManager?.let {
            every { networkInfo.isConnected } returns true
            shadowOf(connectivityManager).setActiveNetworkInfo(networkInfo)
            Truth.assertThat(networkHandler.hasNetworkConnection()).isTrue()
        }
    }
    @Test
    @Config(sdk = [21])
    fun verifyNoConnectedNetworkSdkVersionBelow23_hasNetworkConnection_returnsFalse() {
        connectivityManager?.let {
            every { networkInfo.isConnected } returns false
            shadowOf(connectivityManager).setActiveNetworkInfo(networkInfo)
            Truth.assertThat(networkHandler.hasNetworkConnection()).isFalse()
        }
    }
}