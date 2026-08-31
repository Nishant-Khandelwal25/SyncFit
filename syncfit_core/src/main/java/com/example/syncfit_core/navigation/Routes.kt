package com.example.syncfit_core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes : NavKey {
    @Serializable
    data object OnBoarding : Routes

    @Serializable
    data object Home : Routes

    @Serializable
    data object Workouts : Routes

    @Serializable
    data object History : Routes

    @Serializable
    data object Profile : Routes

    @Serializable
    data object AIFormCheck : Routes
}
