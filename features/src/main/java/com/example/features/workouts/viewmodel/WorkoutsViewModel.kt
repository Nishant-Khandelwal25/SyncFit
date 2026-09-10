package com.example.features.workouts.viewmodel

import com.example.features.R
import com.example.features.workouts.model.ExerciseInfo
import com.example.features.workouts.model.ExerciseUiInfo
import com.example.features.workouts.usecase.WorkoutsUseCase
import com.example.syncfit_core.room.model.ExerciseType
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WorkoutsViewModel @Inject constructor(
    val useCase: WorkoutsUseCase,
) :
    BaseViewModel<WorkoutsUiState, WorkoutsUiAction, WorkoutsUiEvent>(WorkoutsUiState()) {

    init {
        initialiseState()
    }

    private fun initialiseState() {
        launch(
            onError = {
                setState { copy(isLoading = false, errorMessage = "Unable to load workout list") }
            },
        ) {
            val exerciseData = useCase.getAllWorkouts().map { it.toUiModel() }
            setState { copy(isLoading = false, exercisedData = exerciseData) }
        }
    }

    override fun handleAction(action: WorkoutsUiAction) {
        when (action) {
            is WorkoutsUiAction.OnWorkoutClick -> {
                sendEvent { WorkoutsUiEvent.LaunchWorkoutScreen(action.workoutType) }
            }
        }
    }

    private fun ExerciseInfo.toUiModel(): ExerciseUiInfo {
        val resId = when (exerciseType) {
            ExerciseType.SQUAT -> R.drawable.barbell_squat
            ExerciseType.BICEP_CURL -> R.drawable.bicep_curl
            ExerciseType.DEADLIFT -> R.drawable.deadlift
            ExerciseType.DUMBBELL_LATERAL_RAISE -> R.drawable.dumbbell_lateral_raise
            ExerciseType.FLAT_CHEST_PRESS -> R.drawable.flat_chest_press
            ExerciseType.INCLINE_CHEST_PRESS -> R.drawable.incline_chest_press
            ExerciseType.LEG_PRESS -> R.drawable.leg_press
            ExerciseType.SHOULDER_PRESS -> R.drawable.shoulder_press
            ExerciseType.TRICEP_EXTENSION -> R.drawable.straight_bar_tricep_extension
        }

        return ExerciseUiInfo(exerciseName, bodyPart, resId)
    }
}
