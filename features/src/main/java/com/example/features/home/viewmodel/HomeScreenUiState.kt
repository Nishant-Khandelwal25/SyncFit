package com.example.features.home.viewmodel

import com.example.features.home.model.HomeScreenData
import com.example.syncfit_core.healthconnect.model.TodayHealthSummary
import com.example.syncfit_core.viewmodel.UiState

data class HomeScreenUiState(
    var recoveryScore: HomeScreenData = HomeScreenData(),
    var startWorkout: HomeScreenData = HomeScreenData(),
    var quickInsights: HomeScreenData = HomeScreenData(),
    var launchCameraPermissionDialog: Boolean = false,
    var cameraPermissionRequested: Boolean = false,
    var launchSettingsForCameraPermission: Boolean = false,
    val username: String? = null,
    val hasHealthPermission: Boolean = false,
    val isCheckingHealthConnect: Boolean = true,
    val isHealthConnectAvailable: Boolean = false,
    val isLoadingHealthData: Boolean = false,
    val healthSummary: TodayHealthSummary? = null,
    val healthError: String? = null,
) : UiState
