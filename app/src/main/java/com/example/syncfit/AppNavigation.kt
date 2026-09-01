package com.example.syncfit

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FitnessCenter
import androidx.compose.material.icons.filled.History
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.outlined.FitnessCenter
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.ui.NavDisplay
import com.example.features.aiformcheck.ui.AIFormCheckRootView
import com.example.features.aiformcheck.viewmodel.AIFormCheckViewModel
import com.example.features.home.ui.HomeScreenRootView
import com.example.features.home.viewmodel.HomeScreenViewModel
import com.example.features.onboarding.ui.OnboardingRootView
import com.example.features.onboarding.viewmodel.OnboardingViewModel
import com.example.features.workouthistory.ui.WorkoutHistoryRootView
import com.example.features.workouthistory.viewmodel.WorkoutHistoryViewModel
import com.example.syncfit_core.R
import com.example.syncfit_core.navigation.Navigator
import com.example.syncfit_core.navigation.Routes
import com.example.syncfit_core.navigation.model.BottomTab
import com.example.syncfit_core.navigation.rememberNavigationState
import com.example.syncfit_core.navigation.toEntries
import com.example.syncfit_core.ui.components.SamplePlaceHolder
import com.example.syncfit_core.ui.components.SyncFitBottomBar

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
            HomeScreenRootView(viewModel, navigator)
        }

        Routes.Workouts -> NavEntry(route) {
            SamplePlaceHolder(R.string.nav_workout)
        }

        Routes.History -> NavEntry(route) {
            val viewModel: WorkoutHistoryViewModel = hiltViewModel()
            WorkoutHistoryRootView(viewModel)
        }

        Routes.Profile -> NavEntry(route) {
            SamplePlaceHolder(R.string.nav_profile)
        }

        Routes.AIFormCheck -> NavEntry(route) {
            val viewModel: AIFormCheckViewModel = hiltViewModel()
            AIFormCheckRootView(viewModel)
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

    val bottomTabs = listOf(
        BottomTab(
            route = Routes.Home,
            labelResId = R.string.nav_home,
            selectedIcon = Icons.Filled.Home,
            unselectedIcon = Icons.Outlined.Home,
        ),
        BottomTab(
            route = Routes.Workouts,
            labelResId = R.string.nav_workout,
            selectedIcon = Icons.Filled.FitnessCenter,
            unselectedIcon = Icons.Outlined.FitnessCenter,
        ),
        BottomTab(
            route = Routes.History,
            labelResId = R.string.nav_history,
            selectedIcon = Icons.Filled.History,
            unselectedIcon = Icons.Outlined.History,
        ),
        BottomTab(
            route = Routes.Profile,
            labelResId = R.string.nav_profile,
            selectedIcon = Icons.Filled.Person,
            unselectedIcon = Icons.Outlined.Person,
        ),
    )

    val topLevelRoutes = bottomTabs.mapTo(linkedSetOf()) { it.route }
    val state =
        rememberNavigationState(startRoute = Routes.Home, topLevelRoutes = topLevelRoutes)
    val currentRoute = state.backStacks[state.topLevelRoute]?.lastOrNull()
    val navigator = remember(state) { Navigator(state) }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            if (currentRoute in topLevelRoutes) SyncFitBottomBar(
                selectedRoute = state.topLevelRoute,
                bottomTabs = bottomTabs,
                onTabSelected = navigator::navigate,
            )
        },
    ) { innerPadding ->
        NavDisplay(
            entries = state.toEntries(routeEntryProvider(navigator)),
            onBack = navigator::onBack,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        )
    }
}
