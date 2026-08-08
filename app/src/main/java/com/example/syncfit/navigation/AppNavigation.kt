package com.example.syncfit.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay

/**
 * Maps each [Routes] destination to its screen.
 *
 * [Routes.OnBoarding] deliberately has no case here — it's never pushed
 * into [NavigationState]'s back stacks, so
 * this provider only ever receives [Routes.Home].
 */
private fun routeEntryProvider(navigator: Navigator): (NavKey) -> NavEntry<NavKey> = { route ->
    when (route) {
        Routes.Home -> NavEntry(route) {

        }

        else -> error("Unknown Route: $route")
    }
}

@Composable
fun AppNavigation() {
    val state = rememberNavigationState(startRoute = Routes.Home, topLevelRoutes = setOf(Routes.Home))
    val navigator = remember(state) { Navigator(state) }
    NavDisplay(
        entries = state.toEntries(routeEntryProvider(navigator)),
        onBack = { navigator.onBack() },
    )
}
