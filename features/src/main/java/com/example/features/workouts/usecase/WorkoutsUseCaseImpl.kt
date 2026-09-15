package com.example.features.workouts.usecase

import com.example.syncfit_core.api.model.result.WorkoutInfoResult
import com.example.syncfit_core.api.network.RefreshResult
import com.example.syncfit_core.api.repository.ExercisesListRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkoutsUseCaseImpl @Inject constructor(
    private val repository: ExercisesListRepository,
) : WorkoutsUseCase {
    override fun observeExercisesList(): Flow<List<WorkoutInfoResult>> {
        return repository.observeExercisesList()
    }

    override suspend fun refreshExercisesList(): RefreshResult {
        return repository.refreshExercisesList()
    }
}
