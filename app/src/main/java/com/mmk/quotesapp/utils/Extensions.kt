package com.mmk.quotesapp.utils


import android.content.Context
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.paging.LoadState
import com.mmk.quotesapp.ui.base.UiState


val Context.layoutInflater: LayoutInflater
    get() = LayoutInflater.from(this)

fun Context.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    text?.let {
        Toast.makeText(this, it, duration).show()
    }

}

fun Fragment.toast(text: String?, duration: Int = Toast.LENGTH_SHORT) {
    context?.toast(text, duration)
}

inline fun <T : Any> GenericRecyclerViewAdapter<T>.onLoadingStateChanged(
    crossinline onUiStateChanged: (UiState) -> Unit
) {
    this.addLoadStateListener { loadState ->
        with(loadState.source.refresh) {
            val uiState = when (this) {
                is LoadState.NotLoading -> if (this@onLoadingStateChanged.itemCount < 1) UiState.NoData else UiState.HasData
                LoadState.Loading -> UiState.Loading
                is LoadState.Error -> UiState.Error()
            }
            onUiStateChanged(uiState)

        }
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




