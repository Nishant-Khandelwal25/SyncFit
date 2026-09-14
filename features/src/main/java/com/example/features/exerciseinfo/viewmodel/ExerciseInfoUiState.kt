package com.example.features.exerciseinfo.viewmodel

import com.example.syncfit_core.api.model.result.ExerciseInfoResult
import com.example.syncfit_core.viewmodel.UiState

data class ExerciseInfoUiState(
    val loading: Boolean = false,
    val exerciseInfoResult: ExerciseInfoResult? = null,
    val errorMessage: String? = null,
    val resId: Int = 0,
) : UiState
