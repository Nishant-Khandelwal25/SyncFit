package com.example.syncfit_core.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface Routes : NavKey {
    @Serializable
    data object OnBoarding : Routes

    @Serializable
    data object Home: Routes
}
