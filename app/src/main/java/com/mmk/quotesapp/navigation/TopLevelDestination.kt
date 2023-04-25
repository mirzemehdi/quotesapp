package com.mmk.quotesapp.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.mmk.quotesapp.R

enum class TopLevelDestination(
    @DrawableRes val iconResId: Int? = null,
    @StringRes val labelResId: Int
) {
    QUOTES(
        iconResId = R.drawable.ic_quote,
        labelResId = R.string.menu_title_quotes
    ),
    ADD_QUOTE(iconResId = null, labelResId = R.string.menu_title_empty),
    PROFILE(
        iconResId = R.drawable.ic_profile,
        labelResId = R.string.menu_title_profile
    )
}
