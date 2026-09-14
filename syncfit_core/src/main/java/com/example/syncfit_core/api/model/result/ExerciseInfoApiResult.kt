package com.example.syncfit_core.api.model.result

data class ExerciseInfoApiResult(
    val success: Boolean,
    val message: String,
    val exerciseInfo: ExerciseInfoResult?,
)
