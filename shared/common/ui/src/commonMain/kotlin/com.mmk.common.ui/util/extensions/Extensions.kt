package com.mmk.common.ui.util.extensions

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.mmk.common.ui.util.UiMessage
import com.mmk.common.ui.util.toast.ToastMaker
import kotlinx.coroutines.flow.SharedFlow

@Composable
fun SharedFlow<UiMessage?>.showAsToastMessage() {
    LaunchedEffect(key1 = Unit) {
        this@showAsToastMessage.collect { uiMessage ->
            uiMessage?.let { ToastMaker.get().showToast(it) }
        }
    }
}
