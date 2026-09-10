package com.example.features.workouts.viewmodel

import com.example.features.workouts.model.ExerciseUiInfo
import com.example.syncfit_core.viewmodel.UiState

data class WorkoutsUiState(
    val errorMessage: String? = null,
    val isLoading: Boolean = true,
    val exercisedData: List<ExerciseUiInfo> = emptyList(),
) : UiState
