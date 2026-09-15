package com.example.syncfit_core.api.model.result

import kotlinx.serialization.Serializable

@Serializable
data class ExercisesListApiResult(
    val success: Boolean,
    val message: String,
    val exercisesList: List<WorkoutInfoResult>?,
)
