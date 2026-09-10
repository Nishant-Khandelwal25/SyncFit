package com.example.features.home.viewmodel

import com.example.features.healthconnect.HealthConnectUseCase
import com.example.features.home.model.HomeScreenData
import com.example.features.home.usecase.HomeScreenUseCase
import com.example.features.login.usecase.LoginUseCase
import com.example.syncfit_core.healthconnect.model.HealthConnectAvailability
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val useCase: HomeScreenUseCase,
    private val loginUseCase: LoginUseCase,
    private val healthConnectUseCase: HealthConnectUseCase,
) : BaseViewModel<HomeScreenUiState, HomeScreenUiAction, HomeScreenUiEvent>(HomeScreenUiState()) {

    init {
        initialiseState()
        checkHealthConnectStatus()
    }

    private val startWorkOutSection =
        HomeScreenData("Upper body Strength", featureValue = "45 mins - 6 exercises", buttonText = "Start Workout")

    private val quickInsight =
        HomeScreenData(
            "Recovery is looking good",
            featureValue = "You're ready for a normal intensity workout today.",
        )

    private fun initialiseState() {
        launch {
            val isCameraPermissionRequested = useCase.getHasRequestedCameraPermission()
            loginUseCase.username.collectLatest { username ->
                setState {
                    copy(
                        startWorkout = startWorkOutSection,
                        quickInsights = quickInsight,
                        cameraPermissionRequested = isCameraPermissionRequested,
                        username = username.orEmpty(),
                    )
                }
            }
        }
    }

    private fun checkHealthConnectStatus() {
        launch(
            onError = {
                setState {
                    copy(
                        isCheckingHealthConnect = false,
                        healthError = "Unable to check health connect",
                    )
                }
            },
        ) {
            when (healthConnectUseCase.getAvailability()) {
                HealthConnectAvailability.Available -> {
                    setState { copy(isCheckingHealthConnect = false, isHealthConnectAvailable = true) }
                    checkPermissionsAndLoadHealthData()
                }

                HealthConnectAvailability.Unavailable -> {
                    setState {
                        copy(
                            isCheckingHealthConnect = false,
                            healthError = "Health Connect is not available on this device",
                        )
                    }
                }

                HealthConnectAvailability.NeedsProviderUpdate -> {
                    setState {
                        copy(
                            isCheckingHealthConnect = false,
                            healthError = "Update Health Connect to continue",
                        )
                    }
                }
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

            HomeScreenUiAction.ConnectHealthConnect -> {
                connectHealthConnect()
            }

            HomeScreenUiAction.HealthPermissionRequestCompleted -> {
                loadHealthData()
            }

            HomeScreenUiAction.RefreshHealthData -> {
                checkPermissionsAndLoadHealthData()
            }
        }
    }

    private fun connectHealthConnect() {
        launch {
            val hasPermissions = healthConnectUseCase.hasRequiredPermissions()

            if (hasPermissions) {
                loadHealthData()
            } else {
                sendEvent { HomeScreenUiEvent.RequestHealthPermissions(healthConnectUseCase.requiredPermissions) }
            }
        }
    }

    private fun checkPermissionsAndLoadHealthData() {
        launch {
            val hasPermissions = healthConnectUseCase.hasRequiredPermissions()
            setState { copy(hasHealthPermission = hasPermissions) }

            if (hasPermissions) {
                loadHealthData()
            }
        }
    }

    private fun loadHealthData() {
        launch(
            onError = {
                setState {
                    copy(
                        isLoadingHealthData = false,
                        healthError = "Unable to load health data",
                    )
                }
            },
        ) {
            setState { copy(isLoadingHealthData = true, healthError = null) }
            val summary = healthConnectUseCase.readTodaySummary()

            setState { copy(isLoadingHealthData = false, hasHealthPermission = true, healthSummary = summary) }
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
