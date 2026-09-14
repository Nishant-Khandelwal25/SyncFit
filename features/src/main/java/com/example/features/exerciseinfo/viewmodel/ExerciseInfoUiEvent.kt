package com.example.features.exerciseinfo.viewmodel

import com.example.syncfit_core.viewmodel.UiEvent

sealed interface ExerciseInfoUiEvent : UiEvent {
    data class LaunchAIFormCheck(val exerciseName: String) : ExerciseInfoUiEvent
}
