package com.example.syncfit_core.api.model.response

data class ExerciseInfoApiResponse(
    val success: Boolean,
    val message: String? = null,
    val exerciseInfo: ExerciseInfoResponse? = null,
)
