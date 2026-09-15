package com.example.syncfit_core.api.model.response

import kotlinx.serialization.Serializable

@Serializable
data class ExercisesListApiResponse(
    val success: Boolean,
    val message: String? = null,
    val exercisesList: List<WorkoutInfoResponse>? = null,
)
