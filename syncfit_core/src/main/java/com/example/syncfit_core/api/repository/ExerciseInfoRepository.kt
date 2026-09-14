package com.example.syncfit_core.api.repository

import com.example.syncfit_core.api.model.ExerciseInfo
import com.example.syncfit_core.api.network.NetworkResult
import kotlinx.coroutines.flow.Flow

interface ExerciseInfoRepository {
    fun getExerciseInfo(exerciseName: String): Flow<NetworkResult<ExerciseInfo>>
}
