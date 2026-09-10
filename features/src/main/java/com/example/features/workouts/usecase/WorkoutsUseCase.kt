package com.example.features.workouts.usecase

import com.example.features.workouts.model.ExerciseInfo

interface WorkoutsUseCase {
    suspend fun getAllWorkouts(): List<ExerciseInfo>
}
