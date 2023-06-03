package com.mmk.core.util

import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber

class BackgroundExecutor(private val dispatcher: CoroutineDispatcher = Dispatchers.IO) {

    companion object {
        val IO by lazy { BackgroundExecutor(Dispatchers.IO) }
    }

    suspend fun <T> execute(
        func: suspend () -> Result<T>
    ): Result<T> = withContext(dispatcher) {
        try {
            func.invoke()
        } catch (e: CancellationException) {
            Timber.e("Coroutine is cancelled: $e")
            Result.error(ErrorEntity.unexpected(e))
        }
    }
}
