package com.mmk.common.ui.util.extensions

import com.mmk.common.ui.util.toast.ToastMessageHandler
import platform.UIKit.*

fun UIApplication.showToast(text: CharSequence, duration: ToastMessageHandler.Duration) {
    val window = keyWindow ?: return
    if (text.isBlank()) return
    val toastView = UILabel().apply {
        translatesAutoresizingMaskIntoConstraints = false
        backgroundColor = UIColor(white = 0.2, alpha = 0.8)
        textColor = UIColor.whiteColor
        font = UIFont.systemFontOfSize(14.0)
        textAlignment = NSTextAlignmentCenter
        numberOfLines = 0
        setText(text.toString())
    }

    window.addSubview(toastView)
    toastView.centerXAnchor.constraintEqualToAnchor(window.centerXAnchor).setActive(true)
    toastView.centerYAnchor.constraintEqualToAnchor(window.centerYAnchor).setActive(true)
    val time = when (duration) {
        ToastMessageHandler.Duration.LENGTH_SHORT -> 2.0
        ToastMessageHandler.Duration.LENGTH_LONG -> 3.5
    }
    UIView.animateWithDuration(
        time,
        delay = 0.0,
        options = UIViewAnimationOptionShowHideTransitionViews,
        animations = {
            toastView.alpha = 0.0
        }
    ) { _ ->
        toastView.removeFromSuperview()
    }
}
