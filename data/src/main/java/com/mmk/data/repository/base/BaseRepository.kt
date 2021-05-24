package com.mmk.data.repository.base

import com.mmk.data.network.NetworkConstants.NO_INTERNET_CONNECTION
import com.mmk.data.utils.connectivity.Connectivity
import com.mmk.domain.model.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinApiExtension
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

@KoinApiExtension
abstract class BaseRepository : KoinComponent {
    private val connectivity: Connectivity by inject()


    protected suspend fun <T : Any> fetchInBackground(networkDataProvider: () -> Result<T>): Result<T> {

        return if (connectivity.hasConnection()) {
            withContext(Dispatchers.IO) {
                networkDataProvider()
            }
        } else {
            Result.Error(errorCode = NO_INTERNET_CONNECTION)
        }
    }

}