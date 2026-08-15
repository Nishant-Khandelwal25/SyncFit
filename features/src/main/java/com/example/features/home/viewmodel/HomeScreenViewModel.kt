package com.example.features.home.viewmodel

import com.example.features.home.model.HomeScreenData
import com.example.features.home.usecase.HomeScreenUseCase
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val useCase: HomeScreenUseCase,
) :
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
        launch {
            val isCameraPermissionRequested = useCase.getHasRequestedCameraPermission()
            setState {
                copy(
                    healthConnectFeatures = homeScreenDataLists,
                    recoveryScore = recoveryScoreValue,
                    startWorkout = startWorkOutSection,
                    quickInsights = quickInsight,
                    cameraPermissionRequested = isCameraPermissionRequested,
                )
            }
        }
    }

    override fun handleAction(action: HomeScreenUiAction) {
        when (action) {
            HomeScreenUiAction.LaunchCameraPermissionDialog -> {
                setState { copy(launchCameraPermissionDialog = true) }
            }

            HomeScreenUiAction.CameraPermissionRequested -> {
                updateHasRequestedCameraPermission()
            }

            HomeScreenUiAction.CameraPermissionDialogDismiss -> {
                setState { copy(launchCameraPermissionDialog = false) }
            }

            HomeScreenUiAction.CameraPermissionPositiveButtonClick -> {
                setState { copy(launchSettingsForCameraPermission = true) }
            }

            HomeScreenUiAction.SettingsLaunched -> {
                setState { copy(launchSettingsForCameraPermission = false) }
            }

            HomeScreenUiAction.OnStartAIFormCheckClick -> {
                onStartAIFormCheckClick()
            }
        }
    }

    private fun updateHasRequestedCameraPermission() {
        launch {
            useCase.setHasRequestedCameraPermission(true)
            setState { copy(cameraPermissionRequested = true) }
        }
    }

    private fun onStartAIFormCheckClick() {
        sendEvent {
            HomeScreenUiEvent.LaunchAIFormCheck
        }
    }
}
