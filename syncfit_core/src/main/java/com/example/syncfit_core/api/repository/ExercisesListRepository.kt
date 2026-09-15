package com.example.syncfit_core.api.repository

import com.example.syncfit_core.api.model.result.WorkoutInfoResult
import com.example.syncfit_core.api.network.RefreshResult
import kotlinx.coroutines.flow.Flow

interface ExercisesListRepository {
    fun observeExercisesList(): Flow<List<WorkoutInfoResult>>

    suspend fun refreshExercisesList(): RefreshResult
}
