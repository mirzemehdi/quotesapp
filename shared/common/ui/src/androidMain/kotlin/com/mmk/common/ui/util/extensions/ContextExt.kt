package com.mmk.common.ui.util.extensions

import android.content.Context
import android.widget.Toast

fun Context.showToast(text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
    if (text.isEmpty().not())
        Toast.makeText(this, text, duration).show()
}
