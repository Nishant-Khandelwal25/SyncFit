package com.example.features.workouts.viewmodel

import com.example.features.R
import com.example.features.workouts.model.ExerciseUiInfo
import com.example.features.workouts.usecase.WorkoutsUseCase
import com.example.syncfit_core.api.model.result.WorkoutInfoResult
import com.example.syncfit_core.api.network.RefreshResult
import com.example.syncfit_core.room.model.ExerciseType
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    val useCase: WorkoutsUseCase,
) :
    BaseViewModel<WorkoutsUiState, WorkoutsUiAction, WorkoutsUiEvent>(WorkoutsUiState()) {

    private var observeExerciseJob: Job? = null
    private var refreshExerciseJob: Job? = null

    init {
        initialiseState()
    }

    private fun initialiseState() {
        observeExerciseJob?.cancel()
        refreshExerciseJob?.cancel()

        setState {
            WorkoutsUiState(
                isLoading = true,
            )
        }
        observeExerciseJob = launch(
            onError = {
                setState { copy(isLoading = false, errorMessage = "Unable to load workout list") }
            },
        ) {
            useCase.observeExercisesList().collectLatest { exercises ->
                setState {
                    if (exercises.isNotEmpty()) {
                        copy(
                            isLoading = false,
                            exercisesData = exercises.map { it.toUiModel() },
                            errorMessage = null,
                        )
                    } else {
                        copy(exercisesData = emptyList())
                    }
                }
            }
        }

        refreshExerciseJob = launch(
            onError = {
                setState {
                    if (exercisesData.isEmpty()) {
                        copy(isLoading = false, errorMessage = "Unable to refresh workout list.")
                    } else {
                        copy(isLoading = false)
                    }
                }
            },
        ) {
            when (val refreshResult = useCase.refreshExercisesList()) {
                RefreshResult.Success -> {
                    setState { copy(isLoading = false, errorMessage = null) }
                }

                is RefreshResult.Error -> {
                    setState {
                        if (exercisesData.isEmpty()) {
                            copy(isLoading = false, errorMessage = refreshResult.message)
                        } else {
                            copy(isLoading = false)
                        }
                    }
                }
            }
        }
    }

    override fun handleAction(action: WorkoutsUiAction) {
        when (action) {
            is WorkoutsUiAction.OnWorkoutClick -> {
                sendEvent { WorkoutsUiEvent.LaunchWorkoutScreen(action.exerciseName, action.exerciseType) }
            }
        }
    }

    private fun WorkoutInfoResult.toUiModel(): ExerciseUiInfo {
        val resId = when (this.exerciseType) {
            ExerciseType.SQUAT -> R.drawable.barbell_squat
            ExerciseType.BICEP_CURL -> R.drawable.bicep_curl
            ExerciseType.DEADLIFT -> R.drawable.deadlift
            ExerciseType.LATERAL_RAISE -> R.drawable.dumbbell_lateral_raise
            ExerciseType.FLAT_CHEST_PRESS -> R.drawable.flat_chest_press
            ExerciseType.INCLINE_CHEST_PRESS -> R.drawable.incline_chest_press
            ExerciseType.LEG_PRESS -> R.drawable.leg_press
            ExerciseType.SHOULDER_PRESS -> R.drawable.shoulder_press
            ExerciseType.TRICEP_EXTENSION -> R.drawable.straight_bar_tricep_extension
        }

        return ExerciseUiInfo(exerciseName, bodyPart, resId, exerciseType)
    }
}
