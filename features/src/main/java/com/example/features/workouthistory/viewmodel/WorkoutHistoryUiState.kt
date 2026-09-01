package com.example.features.workouthistory.viewmodel

import com.example.syncfit_core.room.entity.ExerciseSession
import com.example.syncfit_core.viewmodel.UiState

data class WorkoutHistoryUiState(
    val exerciseSessions: List<ExerciseSession> = emptyList(),
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
) : UiState
