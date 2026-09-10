package com.example.features.workouts.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.features.workouts.model.ExerciseUiInfo
import com.example.features.workouts.viewmodel.WorkoutsUiAction

@Composable
fun WorkoutsList(exerciseInfo: List<ExerciseUiInfo>, onAction: (WorkoutsUiAction) -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxSize(),
    ) {
        items(count = exerciseInfo.size) { item ->
            WorkoutItem(exerciseInfo[item], onAction)
        }
    }
}
