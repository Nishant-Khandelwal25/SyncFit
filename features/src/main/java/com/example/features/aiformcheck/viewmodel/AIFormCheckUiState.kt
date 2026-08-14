package com.example.features.aiformcheck.viewmodel

import androidx.camera.core.SurfaceRequest
import com.example.syncfit_core.viewmodel.UiState

data class AIFormCheckUiState(
    var surfaceRequest: SurfaceRequest? = null,
) : UiState
