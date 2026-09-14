package com.example.features.exerciseinfo.usecase

import com.example.syncfit_core.api.model.result.ExerciseInfoResult
import com.example.syncfit_core.api.network.RefreshResult
import com.example.syncfit_core.api.repository.ExerciseInfoRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ExerciseInfoUseCaseImpl @Inject constructor(
    private val repository: ExerciseInfoRepository,
) : ExerciseInfoUseCase {
    override fun observeExerciseInfo(exerciseName: String): Flow<ExerciseInfoResult?> {
        return repository.observeExerciseInfo(exerciseName)
    }

    override suspend fun refreshExerciseInfo(exerciseName: String): RefreshResult {
        return repository.refreshExerciseInfo(exerciseName)
    }

}
