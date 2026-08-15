package com.example.features.home.viewmodel

import com.example.features.home.model.HomeScreenData
import com.example.syncfit_core.viewmodel.UiState

data class HomeScreenUiState(
    var healthConnectFeatures: List<HomeScreenData> = emptyList(),
    var recoveryScore: HomeScreenData = HomeScreenData(),
    var startWorkout: HomeScreenData = HomeScreenData(),
    var quickInsights: HomeScreenData = HomeScreenData(),
    var launchCameraPermissionDialog: Boolean = false,
    var cameraPermissionRequested: Boolean = false,
    var launchSettingsForCameraPermission: Boolean = false,
) : UiState
