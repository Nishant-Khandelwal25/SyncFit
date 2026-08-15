package com.example.features.home.viewmodel

import com.example.syncfit_core.viewmodel.UiEvent

sealed interface HomeScreenUiEvent : UiEvent {
    data object LaunchAIFormCheck : HomeScreenUiEvent
}
