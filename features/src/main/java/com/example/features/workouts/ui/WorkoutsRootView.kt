package com.example.features.workouts.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.features.workouts.viewmodel.WorkoutsUiEvent
import com.example.features.workouts.viewmodel.WorkoutsViewModel
import com.example.syncfit_core.navigation.Navigator
import com.example.syncfit_core.navigation.Routes
import com.example.syncfit_core.viewmodel.ObserveAsEvents

@Composable
fun WorkoutsRootView(viewModel: WorkoutsViewModel, navigator: Navigator) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is WorkoutsUiEvent.LaunchWorkoutScreen -> {
                navigator.navigate(Routes.WorkoutInformation(event.workoutName))
            }
        }
    }
    WorkoutsInformation(state, viewModel::onAction)
}
