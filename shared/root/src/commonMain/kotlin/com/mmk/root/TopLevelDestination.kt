package com.mmk.root

import com.mmk.common.ui.MR
import dev.icerock.moko.resources.ImageResource
import dev.icerock.moko.resources.StringResource

enum class TopLevelDestination(
    val route: String,
    val iconRes: ImageResource? = null,
    val labelStringResource: StringResource
) {
    QUOTES(
        route = "route_all_quotes",
        iconRes = MR.images.ic_quote,
        labelStringResource = MR.strings.menu_title_quotes
    ),
    ADD_QUOTE(
        route = "route_add_new_quote",
        iconRes = null,
        labelStringResource = MR.strings.menu_title_empty
    ),
    PROFILE(
        route = "route_profile",
        iconRes = MR.images.ic_profile,
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
