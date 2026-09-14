package com.example.features.workouts.usecase

import com.example.features.workouts.model.WorkoutInfo

interface WorkoutsUseCase {
    suspend fun getAllWorkouts(): List<WorkoutInfo>
}
