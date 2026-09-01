package com.example.features.workouthistory.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.features.workouthistory.viewmodel.WorkoutHistoryViewModel

@Composable
fun WorkoutHistoryRootView(viewModel: WorkoutHistoryViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    WorkoutHistorySession(state)
}
