package com.mmk.common.ui.util.extensions

import android.content.Context
import android.widget.Toast
import com.mmk.common.ui.util.UiMessage

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    if (text.isEmpty().not())
        Toast.makeText(this, text, duration).show()
}

fun Context.showToast(uiMessage: UiMessage, duration: Int = Toast.LENGTH_SHORT) {
    val text = uiMessage.getMessage(this)
    showToast(text = text, duration = duration)
}
