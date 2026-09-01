package com.example.features.workouthistory.domain

import com.example.syncfit_core.room.entity.ExerciseSession
import kotlinx.coroutines.flow.Flow

interface WorkoutHistoryUseCase {
    fun getExerciseInfo(): Flow<List<ExerciseSession>>
}
