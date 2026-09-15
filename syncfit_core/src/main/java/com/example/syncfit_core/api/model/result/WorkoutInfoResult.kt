package com.example.syncfit_core.api.model.result

import com.example.syncfit_core.room.model.ExerciseType
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutInfoResult(
    val id: Int,
    val exerciseName: String,
    val bodyPart: String,
    val exerciseType: ExerciseType,
)
