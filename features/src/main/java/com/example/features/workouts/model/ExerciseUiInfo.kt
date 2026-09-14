package com.example.features.workouts.model

import com.example.syncfit_core.room.model.ExerciseType

data class ExerciseUiInfo(
    val exerciseName: String,
    val bodyPart: String,
    val resId: Int,
    val exerciseType: ExerciseType,
)
