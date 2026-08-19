package com.example.features.aiformcheck.viewmodel

import androidx.camera.core.SurfaceRequest
import com.example.features.aiformcheck.data.camera.CameraFrameInfo
import com.example.features.aiformcheck.domain.model.Pose
import com.example.syncfit_core.viewmodel.UiState

data class AIFormCheckUiState(
    var surfaceRequest: SurfaceRequest? = null,
    val pose: Pose? = null,
    val isPoseDetected: Boolean = false,
    val error: String? = null,
    val frameInfo: CameraFrameInfo? = null,
) : UiState
