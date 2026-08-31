package com.example.syncfit_core.localRepository

import com.example.syncfit_core.room.entity.ExerciseSession

interface SyncFitDBRepository {
    suspend fun getExerciseInfo(): List<ExerciseSession>

    suspend fun upsertExerciseInfo(exerciseSession: ExerciseSession)
}
