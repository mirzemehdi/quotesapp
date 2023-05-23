package com.mmk.core.util

import com.mmk.core.model.ErrorEntity
import com.mmk.core.model.Result
import com.mmk.core.util.logger.AppLogger
import kotlinx.coroutines.*

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
            AppLogger.e("Coroutine is cancelled: $e")
            Result.error(ErrorEntity.unexpected(e))
        }
    }
}
