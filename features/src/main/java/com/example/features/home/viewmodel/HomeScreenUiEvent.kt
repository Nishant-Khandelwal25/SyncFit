package com.example.features.home.viewmodel

import com.example.syncfit_core.viewmodel.UiEvent

sealed interface HomeScreenUiEvent : UiEvent {
    data class LaunchAIFormCheck(val exerciseName: String) : HomeScreenUiEvent
    data class RequestHealthPermissions(val permissions: Set<String>) : HomeScreenUiEvent
}
