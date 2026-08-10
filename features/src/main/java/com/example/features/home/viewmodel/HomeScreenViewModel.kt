package com.example.features.home.viewmodel

import com.example.features.home.model.HomeScreenData
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor() :
    BaseViewModel<HomeScreenUiState, HomeScreenUiAction, HomeScreenUiEvent>(HomeScreenUiState()) {
    // Will make it dynamic once health connect integration is done
    private val homeScreenDataLists = listOf(
        HomeScreenData("Sleep", "7h 45m", "Good"),
        HomeScreenData("HRV", "58ms", "Good"),
    )

    private val recoveryScoreValue = HomeScreenData(
        featureName = "Recovery Score",
        featureValue = "82",
        featureStatus = "Good",
        changeInValue = "12 points higher vs yesterday",
    )
    private val startWorkOutSection =
        HomeScreenData("Upper Body Strength", featureValue = "45 mins - 6 exercises", buttonText = "Start Workout")

    private val quickInsight =
        HomeScreenData(
            "Recovery is looking good",
            featureValue = "You're ready for a normal intensity workout today.",
        )

    fun onCreate() {
        initialiseState()
    }

    private fun initialiseState() {
        setState {
            copy(
                healthConnectFeatures = homeScreenDataLists,
                recoveryScore = recoveryScoreValue,
                startWorkout = startWorkOutSection,
                quickInsights = quickInsight,
            )
        }
    }

    override fun handleAction(action: HomeScreenUiAction) {

    }
}
