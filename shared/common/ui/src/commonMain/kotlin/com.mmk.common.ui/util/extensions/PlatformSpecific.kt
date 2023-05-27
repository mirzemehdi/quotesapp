package com.mmk.common.ui.util.extensions

import androidx.compose.runtime.Composable
import com.mmk.common.ui.util.UiMessage
import kotlinx.coroutines.flow.SharedFlow

@Composable
expect fun SharedFlow<UiMessage?>.showAsToastMessage()