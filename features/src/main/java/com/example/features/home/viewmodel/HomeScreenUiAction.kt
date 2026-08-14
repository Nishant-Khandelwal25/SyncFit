package com.example.features.home.viewmodel

import com.example.syncfit_core.viewmodel.UiAction

sealed interface HomeScreenUiAction : UiAction {
    data object LaunchCameraPermissionDialog : HomeScreenUiAction
    data object CameraPermissionRequested : HomeScreenUiAction
    data object CameraPermissionDialogDismiss : HomeScreenUiAction
    data object CameraPermissionPositiveButtonClick : HomeScreenUiAction
    data object SettingsLaunched : HomeScreenUiAction
    data object OnStartAIFormCheckClick : HomeScreenUiAction
}
