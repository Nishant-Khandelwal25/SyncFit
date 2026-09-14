package com.example.syncfit_core.api.model

data class ExerciseInfoResponse(
    val success: Boolean,
    val message: String? = null,
    val exerciseInfo: ExerciseInfo? = null,
)
