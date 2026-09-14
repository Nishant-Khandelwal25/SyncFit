package com.example.features.exerciseinfo.viewmodel

import com.example.features.R
import com.example.features.exerciseinfo.usecase.ExerciseInfoUseCase
import com.example.syncfit_core.api.network.RefreshResult
import com.example.syncfit_core.room.model.ExerciseType
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class ExerciseInfoViewModel @Inject constructor(
    private val useCase: ExerciseInfoUseCase,
) : BaseViewModel<ExerciseInfoUiState, ExerciseInfoUiAction, ExerciseInfoUiEvent>(ExerciseInfoUiState()) {
    private var observeExerciseJob: Job? = null
    private var refreshExerciseJob: Job? = null
    fun loadExerciseInfo(exerciseName: String) {
        observeExerciseJob?.cancel()
        refreshExerciseJob?.cancel()
        val exerciseResId = setWorkoutResId(exerciseName)

        setState {
            ExerciseInfoUiState(
                loading = true,
                resId = exerciseResId,
            )
        }

        observeExerciseJob = launch(
            onError = {
                setState {
                    copy(
                        loading = false,
                        errorMessage = "Unable to read saved exercise information.",
                    )
                }
            },
        ) {
            useCase.observeExerciseInfo(exerciseName).collectLatest { result ->
                setState {
                    if (result != null) {
                        copy(
                            loading = false,
                            exerciseInfoResult = result,
                            errorMessage = null,
                            resId = exerciseResId,
                        )
                    } else {
                        copy(exerciseInfoResult = null, resId = exerciseResId)
                    }
                }
            }
        }
        refreshExerciseJob = launch(
            onError = {
                setState {
                    copy(loading = false, errorMessage = "Something went wrong")
                }
            },
        ) {
            when (val refreshResult = useCase.refreshExerciseInfo(exerciseName)) {
                RefreshResult.Success -> Unit

                is RefreshResult.Error -> {
                    setState {
                        if (exerciseInfoResult == null) {
                            copy(
                                loading = false,
                                errorMessage = refreshResult.message,
                            )
                        } else {
                            copy(loading = false)
                        }
                    }
                }
            }
        }
    }

    private fun setWorkoutResId(workoutName: String): Int {
        return when {
            workoutName.equals(ExerciseType.SQUAT.name, true) -> R.drawable.barbell_squat
            workoutName.equals(ExerciseType.BICEP_CURL.name, true) -> R.drawable.bicep_curl
            workoutName.equals(ExerciseType.DEADLIFT.name, true) -> R.drawable.deadlift
            workoutName.equals(ExerciseType.LATERAL_RAISE.name, true) -> R.drawable.dumbbell_lateral_raise
            workoutName.equals(ExerciseType.FLAT_CHEST_PRESS.name, true) -> R.drawable.flat_chest_press
            workoutName.equals(ExerciseType.INCLINE_CHEST_PRESS.name, true) -> R.drawable.incline_chest_press
            workoutName.equals(ExerciseType.LEG_PRESS.name, true) -> R.drawable.leg_press
            workoutName.equals(ExerciseType.SHOULDER_PRESS.name, true) -> R.drawable.shoulder_press
            workoutName.equals(ExerciseType.TRICEP_EXTENSION.name, true) -> R.drawable.straight_bar_tricep_extension
            else -> 0
        }
    }

    override fun handleAction(action: ExerciseInfoUiAction) {
        when (action) {
            is ExerciseInfoUiAction.StartAiFormCheck -> {
                sendEvent {
                    ExerciseInfoUiEvent.LaunchAIFormCheck(action.exerciseName)
                }
            }

            is ExerciseInfoUiAction.WatchDemonstration -> {}
        }
    }
}
