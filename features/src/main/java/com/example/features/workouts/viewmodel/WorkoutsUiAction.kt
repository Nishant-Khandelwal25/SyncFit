package com.example.features.workouts.viewmodel

import com.example.syncfit_core.viewmodel.UiAction

sealed interface WorkoutsUiAction : UiAction {
    data class OnWorkoutClick(val workoutType: String) : WorkoutsUiAction
}
