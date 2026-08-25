package com.example.features.aiformcheck.viewmodel

import androidx.camera.core.SurfaceRequest
import com.example.features.aiformcheck.data.camera.CameraFrameInfo
import com.example.features.aiformcheck.data.exercise.SquatResult
import com.example.features.aiformcheck.domain.model.Pose
import com.example.syncfit_core.room.model.ExerciseType
import com.example.syncfit_core.viewmodel.UiState

data class AIFormCheckUiState(
    val surfaceRequest: SurfaceRequest? = null,
    val pose: Pose? = null,
    val isPoseDetected: Boolean = false,
    val error: String? = null,
    val frameInfo: CameraFrameInfo? = null,
    val squatResult: SquatResult? = null,
    val startCountingReps: Boolean = false,
    val startTime: Long? = null,
    val endTime: Long? = null,
    var totalReps: Int? = 0,
    val totalSets: Int? = 0,
    val exerciseType: ExerciseType = ExerciseType.SQUAT,
    val sessionStarted: Boolean = false,
) : UiState
