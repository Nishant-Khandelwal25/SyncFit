package com.example.features.workouts.viewmodel

import com.example.syncfit_core.viewmodel.UiEvent

sealed interface WorkoutsUiEvent : UiEvent {
    data class LaunchWorkoutScreen(val workoutName: String) : WorkoutsUiEvent
}
