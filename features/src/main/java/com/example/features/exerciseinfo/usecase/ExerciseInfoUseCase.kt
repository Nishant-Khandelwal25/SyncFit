package com.example.features.exerciseinfo.usecase

import com.example.syncfit_core.api.model.result.ExerciseInfoResult
import com.example.syncfit_core.api.network.RefreshResult
import kotlinx.coroutines.flow.Flow

interface ExerciseInfoUseCase {
    fun observeExerciseInfo(exerciseName: String): Flow<ExerciseInfoResult?>
    suspend fun refreshExerciseInfo(
        exerciseName: String,
    ): RefreshResult
}
