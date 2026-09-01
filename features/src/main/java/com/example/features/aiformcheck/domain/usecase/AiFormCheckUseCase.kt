package com.example.features.aiformcheck.domain.usecase

import com.example.features.aiformcheck.domain.model.Pose
import com.example.syncfit_core.room.entity.ExerciseSession
import kotlinx.coroutines.flow.Flow

interface AiFormCheckUseCase {
    val poses: Flow<Pose>

    suspend fun upsertExerciseInfo(sessionInfo: ExerciseSession)
}
