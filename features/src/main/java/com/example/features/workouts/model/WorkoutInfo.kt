package com.example.features.workouts.model

import com.example.syncfit_core.room.model.ExerciseType

data class WorkoutInfo(
    val exerciseName: String,
    val bodyPart: String,
    val exerciseType: ExerciseType,
)
