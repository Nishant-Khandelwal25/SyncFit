package com.example.syncfit

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.example.features.home.ui.HomeScreenRootView
import com.example.features.home.viewmodel.HomeScreenViewModel
import com.example.features.onboarding.ui.OnboardingRootView
import com.example.features.onboarding.viewmodel.OnboardingViewModel
import com.example.syncfit_core.navigation.Navigator
import com.example.syncfit_core.navigation.Routes
import com.example.syncfit_core.navigation.rememberNavigationState
import com.example.syncfit_core.navigation.toEntries

/**
 * Maps each [com.example.syncfit_core.navigation.Routes] destination to its screen.
 *
 * [com.example.syncfit_core.navigation.Routes.OnBoarding] deliberately has no case here — it's never pushed
 * into [com.example.syncfit_core.navigation.NavigationState]'s back stacks, so
 * this provider only ever receives [com.example.syncfit_core.navigation.Routes.Home].
 */
private fun routeEntryProvider(navigator: Navigator): (NavKey) -> NavEntry<NavKey> = { route ->
    when (route) {
        Routes.Home -> NavEntry(route) {
            val viewModel: HomeScreenViewModel = hiltViewModel()
            HomeScreenRootView(viewModel)
        }

        else -> error("Unknown Route: $route")
    }
}

@Composable
fun AppNavigation(
    hasOnboarded: Boolean,
) {
    if (!hasOnboarded) {
        val viewModel: OnboardingViewModel = hiltViewModel()
        OnboardingRootView(viewModel)
        return
    }
    val state =
        rememberNavigationState(startRoute = Routes.Home, topLevelRoutes = setOf(Routes.Home))
    val navigator = remember(state) { Navigator(state) }
    NavDisplay(
        entries = state.toEntries(routeEntryProvider(navigator)),
        onBack = { navigator.onBack() },
    )
}
