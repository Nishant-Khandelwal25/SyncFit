package com.example.features.aiformcheck.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.features.aiformcheck.data.camera.CameraController
import com.example.features.aiformcheck.domain.exercise.SquatAnalyzer
import com.example.features.aiformcheck.domain.repository.PoseRepository
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

@HiltViewModel
class AIFormCheckViewModel @Inject constructor(
    private val cameraController: CameraController,
    private val poseRepository: PoseRepository,
    private val squatAnalyzer: SquatAnalyzer,
) :
    BaseViewModel<AIFormCheckUiState, AIFormCheckUiAction, AIFormCheckUiEvent>(AIFormCheckUiState()) {

    private val _shouldStartCountingReps =
        MutableStateFlow(false)

    val shouldStartCountingReps =
        _shouldStartCountingReps.asStateFlow()

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
            combine(
                poseRepository.poses,
                shouldStartCountingReps,
            ) { pose, shouldCountReps -> pose to shouldCountReps }.collectLatest { (pose, shouldCount) ->
                setState {
                    copy(
                        pose = pose,
                        isPoseDetected = pose.landmarks.isNotEmpty(),
                    )
                }
                if (!shouldCount) {
                    return@collectLatest
                }
                val squatResult = squatAnalyzer.process(pose)
                setState {
                    copy(squatResult = squatResult)
                }
            }
        }
    }

    suspend fun bindToCamera(applicationContext: Context, lifecycleOwner: LifecycleOwner) {
        cameraController.bind(applicationContext, lifecycleOwner)
    }

    override fun handleAction(action: AIFormCheckUiAction) {
        when (action) {
            is AIFormCheckUiAction.StartExercise -> {
                shouldStartRepCounting(action.shouldStartCountingReps)
            }
        }
    }

    private fun shouldStartRepCounting(shouldStartCountingReps: Boolean) {
        if (shouldStartCountingReps) clearRepsCount()
        _shouldStartCountingReps.value = shouldStartCountingReps
        setState { copy(startCountingReps = shouldStartCountingReps) }
    }

    fun clearRepsCount() {
        squatAnalyzer.reset()
    }

    override fun onCleared() {
        cameraController.close()
        clearRepsCount()
        super.onCleared()
    }

}
