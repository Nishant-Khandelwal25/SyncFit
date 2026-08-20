package com.example.features.aiformcheck.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.features.aiformcheck.data.camera.CameraController
import com.example.features.aiformcheck.domain.exercise.SquatAnalyzer
import com.example.features.aiformcheck.domain.repository.PoseRepository
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class AIFormCheckViewModel @Inject constructor(
    private val cameraController: CameraController,
    private val poseRepository: PoseRepository,
    private val squatAnalyzer: SquatAnalyzer,
) :
    BaseViewModel<AIFormCheckUiState, AIFormCheckUiAction, AIFormCheckUiEvent>(AIFormCheckUiState()) {

    init {
        setupPreview()
        observePose()
        observeCamera()
    }

    private fun observeCamera() {
        launch {
            cameraController.frameInfo.collectLatest { frameInfo ->
                setState { copy(frameInfo = frameInfo) }
            }
        }
    }

    private fun setupPreview() {
        cameraController.preview.setSurfaceProvider {
            cameraController.preview.setSurfaceProvider { newSurfaceRequest ->
                setState { copy(surfaceRequest = newSurfaceRequest) }
            }
        }
    }

    private fun observePose() {
        launch {
            poseRepository.poses.collectLatest { pose ->
                val squatResult = squatAnalyzer.process(pose)
                setState {
                    copy(
                        pose = pose,
                        isPoseDetected = pose.landmarks.isNotEmpty(),
                        squatResult = squatResult,
                    )
                }
            }
        }
    }

    suspend fun bindToCamera(applicationContext: Context, lifecycleOwner: LifecycleOwner) {
        cameraController.bind(applicationContext, lifecycleOwner)
    }

    override fun handleAction(action: AIFormCheckUiAction) {

    }

    override fun onCleared() {
        cameraController.close()
        super.onCleared()
    }

}
