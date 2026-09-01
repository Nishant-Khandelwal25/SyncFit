package com.example.features.workouthistory.domain

import com.example.syncfit_core.localRepository.SyncFitDBRepository
import com.example.syncfit_core.room.entity.ExerciseSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class WorkoutHistoryUseCaseImpl @Inject constructor(
    private val dbRepository: SyncFitDBRepository,
) :
    WorkoutHistoryUseCase {
    override fun getExerciseInfo(): Flow<List<ExerciseSession>> {
        return dbRepository.getExerciseInfo()
    }
}
