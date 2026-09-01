package com.example.features.workouthistory.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.features.R
import com.example.features.workouthistory.viewmodel.WorkoutHistoryUiState
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.SyncFitTypography

@Composable
fun WorkoutHistorySession(state: WorkoutHistoryUiState) {
    when {
        state.isLoading -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        state.errorMessage != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                SyncFitText(
                    text = state.errorMessage,
                    textStyle = SyncFitTypography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }

        state.exerciseSessions.isEmpty() -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                SyncFitText(
                    text = stringResource(R.string.no_workout_recorded),
                    textStyle = SyncFitTypography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }

        else -> {
            WorkoutHistoryList(state.exerciseSessions)
        }
    }
}
