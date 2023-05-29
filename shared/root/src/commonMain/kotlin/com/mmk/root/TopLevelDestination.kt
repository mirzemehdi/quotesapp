package com.mmk.root

import com.mmk.common.ui.MR
import dev.icerock.moko.resources.StringResource

enum class TopLevelDestination(
    val route: String,
    val iconResPath: String? = null,
    val labelStringResource: StringResource
) {
    QUOTES(
        route = "route_all_quotes",
        iconResPath = "drawable/ic_bottom_quote.xml",
        labelStringResource = MR.strings.menu_title_quotes
    ),
    ADD_QUOTE(
        route = "route_add_new_quote",
        iconResPath = null,
        labelStringResource = MR.strings.menu_title_empty
    ),
    PROFILE(
        route = "route_profile",
        iconResPath = "drawable/ic_profile.xml",
        labelStringResource = MR.strings.menu_title_profile
    );
    companion object {
        fun getDestinationFromRoute(route: String): TopLevelDestination {
            return when (route) {
                ADD_QUOTE.route -> ADD_QUOTE
                QUOTES.route -> QUOTES
                PROFILE.route -> PROFILE
                else -> QUOTES
            }
        }
    }
}
