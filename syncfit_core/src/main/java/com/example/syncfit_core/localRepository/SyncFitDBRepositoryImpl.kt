package com.example.syncfit_core.localRepository

import com.example.syncfit_core.room.dao.ExerciseSessionDao
import com.example.syncfit_core.room.entity.ExerciseSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SyncFitDBRepositoryImpl @Inject constructor(
    private var exerciseSessionDao: ExerciseSessionDao,
) : SyncFitDBRepository {
    override fun getExerciseInfo(): Flow<List<ExerciseSession>> {
        return exerciseSessionDao.getExerciseInfo()
    }

    override suspend fun upsertExerciseInfo(exerciseSession: ExerciseSession) {
        exerciseSessionDao.upsertExerciseInfo(exerciseSession)
    }
}
