package com.example.syncfit_core.api.model.response

import com.example.syncfit_core.room.model.ExerciseType
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutInfoResponse(
    val id: Int?,
    val exerciseName: String?,
    val bodyPart: String?,
    val exerciseType: ExerciseType?,
)
