package com.example.features.aiformcheck.viewmodel

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import com.example.features.aiformcheck.data.camera.CameraController
import com.example.features.aiformcheck.domain.exercise.SquatAnalyzer
import com.example.features.aiformcheck.domain.usecase.AiFormCheckUseCase
import com.example.syncfit_core.room.entity.ExerciseSession
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
    private val aiFormCheckUseCase: AiFormCheckUseCase,
    private val squatAnalyzer: SquatAnalyzer,
) : BaseViewModel<AIFormCheckUiState, AIFormCheckUiAction, AIFormCheckUiEvent>(AIFormCheckUiState()) {

    private var totalReps = 0
    private var totalSets = 0

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
        cameraController.preview.setSurfaceProvider { newSurfaceRequest ->
            setState { copy(surfaceRequest = newSurfaceRequest) }
        }
    }

    private fun observePose() {
        launch {
            combine(
                aiFormCheckUseCase.poses,
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

            is AIFormCheckUiAction.EndExercise -> sessionEnd()
        }
    }

    private fun shouldStartRepCounting(shouldStartCountingReps: Boolean) {
        if (shouldStartCountingReps) {
            startSession()
            clearRepsCount()
        } else {
            totalSets++
            totalReps += state.value.squatResult?.reps ?: 0
        }
        _shouldStartCountingReps.value = shouldStartCountingReps
        setState {
            copy(
                startCountingReps = shouldStartCountingReps,
                totalReps = totalReps,
                totalSets = totalSets,
            )
        }
    }

    private fun startSession() {
        val sessionStarted = state.value.sessionStarted
        if (!sessionStarted) {
            setState { copy(sessionStarted = true, startTime = System.currentTimeMillis()) }
        }
    }

    private fun clearRepsCount() {
        squatAnalyzer.reset()
    }

    private fun sessionEnd() {
        val startTime = currentState.startTime ?: System.currentTimeMillis()
        val endTime = System.currentTimeMillis()
        if (_shouldStartCountingReps.value) {
            totalSets++
            totalReps += currentState.squatResult?.reps ?: 0
            _shouldStartCountingReps.value = false
        }
        setState {
            copy(
                sessionStarted = false,
                endTime = endTime,
                totalReps = totalReps,
                totalSets = totalSets,
                startCountingReps = false,
                squatResult = null,
            )
        }
        if (totalReps > 0) {
            updateSessionDetailsInDB(totalReps, totalSets, startTime, endTime)
        }
        clearRepsCount()
    }

    private fun updateSessionDetailsInDB(
        totalReps: Int,
        totalSets: Int,
        startTime: Long,
        endTime: Long,
    ) {
        launch {
            val sessionInfo = ExerciseSession(
                exerciseType = currentState.exerciseType,
                repsCount = totalReps,
                setsCount = totalSets,
                startedAt = startTime,
                endedAt = endTime,
            )
            aiFormCheckUseCase.upsertExerciseInfo(sessionInfo)
        }
    }

    override fun onCleared() {
        cameraController.close()
        clearRepsCount()
        super.onCleared()
    }

}
