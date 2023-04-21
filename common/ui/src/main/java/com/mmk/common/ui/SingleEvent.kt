package com.mmk.common.ui

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
class SingleEvent<out T>(private val content: T) {

    var hasBeenHandled = false
        private set // Allow external read but not write

    /**
     * Returns the content and prevents its use again.
     */
    fun getContentIfNotHandled(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }

    /**
     * Returns the content, even if it's already been handled.
     */
    fun peekContent(): T = content
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as SingleEvent<*>

        if (content != other.content) return false
        if (hasBeenHandled != other.hasBeenHandled) return false

        return true
    }

    override fun hashCode(): Int {
        var result = content?.hashCode() ?: 0
        result = 31 * result + hasBeenHandled.hashCode()
        return result
    }
}

inline fun <T> LiveData<SingleEvent<T>>.observeEvent(
    owner: LifecycleOwner,
    crossinline onEventUnhandledContent: (T) -> Unit
) {
    observe(owner) {
        it?.getContentIfNotHandled()?.let(onEventUnhandledContent)
    }
}
@SuppressLint("ComposableNaming")
@Composable
inline fun <T> LiveData<SingleEvent<T>>.observeEvent(
    crossinline onEventUnhandledContent: (T) -> Unit
) {

    val singleEventValue by this.observeAsState()
    LaunchedEffect(key1 = singleEventValue) {
        singleEventValue?.getContentIfNotHandled()?.let(onEventUnhandledContent)
    }
}
