package com.example.features.workouthistory.viewmodel

import com.example.features.workouthistory.domain.WorkoutHistoryUseCase
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

@HiltViewModel
class WorkoutHistoryViewModel @Inject constructor(
    private val workoutHistoryUseCase: WorkoutHistoryUseCase,
) : BaseViewModel<WorkoutHistoryUiState, WorkoutHistoryUiAction, WorkoutHistoryUiEvent>(
    WorkoutHistoryUiState(),
) {

    init {
        fetchWorkoutHistory()
    }

    private fun fetchWorkoutHistory() {
        launch(
            onError = { throwable ->
                setState {
                    copy(
                        isLoading = false,
                        errorMessage = throwable.message ?: "Unable to load workout history",
                    )
                }
            },
        ) {
            workoutHistoryUseCase.getExerciseInfo().distinctUntilChanged().collect { sessions ->
                setState { copy(exerciseSessions = sessions, isLoading = false, errorMessage = null) }
            }
        }
    }

    override fun handleAction(action: WorkoutHistoryUiAction) {

    }
}
