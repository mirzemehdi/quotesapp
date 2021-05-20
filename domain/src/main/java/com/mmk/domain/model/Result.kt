package com.mmk.domain.model

sealed class Result<out T> {

    data class Success<T>(val data: T) : Result<T>()
    data class Error<T>(val message: String? = "", val errorCode: Int? = null) : Result<T>()

}

inline fun <T : Any> Result<T>.onSuccess(action: (T) -> Unit): Result<T> {
    if (this is Result.Success) action(data)
    return this
}

inline fun <T : Any> Result<T>.onError(action: (message: String?, errorCode: Int?) -> Unit) {
    if (this is Result.Error) action(message, errorCode)
}

