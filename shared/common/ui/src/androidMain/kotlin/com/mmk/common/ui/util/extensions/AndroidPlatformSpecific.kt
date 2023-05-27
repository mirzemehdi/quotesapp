package com.mmk.common.ui.util.extensions

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import com.mmk.common.ui.util.UiMessage
import kotlinx.coroutines.flow.SharedFlow

@SuppressLint("ComposableNaming")
@Composable
actual fun SharedFlow<UiMessage?>.showAsToastMessage() {
    val context = LocalContext.current
    LaunchedEffect(key1 = Unit) {
        this@showAsToastMessage.collect { uiMessage ->
            uiMessage?.let { context.showToast(it) }
        }
    }
}