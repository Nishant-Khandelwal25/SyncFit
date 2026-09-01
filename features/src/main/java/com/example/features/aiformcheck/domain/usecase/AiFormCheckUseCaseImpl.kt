package com.example.features.aiformcheck.domain.usecase

import com.example.features.aiformcheck.domain.model.Pose
import com.example.features.aiformcheck.domain.repository.PoseRepository
import com.example.syncfit_core.localRepository.SyncFitDBRepository
import com.example.syncfit_core.room.entity.ExerciseSession
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AiFormCheckUseCaseImpl @Inject constructor(
    private val poseRepository: PoseRepository,
    private val dbRepository: SyncFitDBRepository,
) :
    AiFormCheckUseCase {
    override val poses: Flow<Pose>
        get() = poseRepository.poses

    override suspend fun upsertExerciseInfo(sessionInfo: ExerciseSession) {
        dbRepository.upsertExerciseInfo(sessionInfo)
    }

}
