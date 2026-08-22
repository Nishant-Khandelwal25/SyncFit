package com.example.features.aiformcheck.viewmodel

import com.example.syncfit_core.viewmodel.UiAction

sealed interface AIFormCheckUiAction : UiAction {
    data class StartExercise(var shouldStartCountingReps: Boolean) : AIFormCheckUiAction
}
