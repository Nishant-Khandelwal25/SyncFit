package com.example.features.workouts.model

import com.example.syncfit_core.room.model.ExerciseType

data class ExerciseInfo(
    val exerciseName: String,
    val bodyPart: String,
    val exerciseType: ExerciseType,
)
