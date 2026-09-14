package com.example.features.exerciseinfo.viewmodel

import com.example.syncfit_core.viewmodel.UiAction

sealed interface ExerciseInfoUiAction : UiAction {
    data class StartAiFormCheck(val exerciseName: String) : ExerciseInfoUiAction
    data class WatchDemonstration(val demonstrationLink: String) : ExerciseInfoUiAction
}
