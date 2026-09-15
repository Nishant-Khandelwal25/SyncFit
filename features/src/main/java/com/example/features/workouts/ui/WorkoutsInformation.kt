package com.example.features.workouts.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.example.features.R
import com.example.features.workouts.viewmodel.WorkoutsUiAction
import com.example.features.workouts.viewmodel.WorkoutsUiState
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.SyncFitTypography

@Composable
fun WorkoutsInformation(state: WorkoutsUiState, onAction: (WorkoutsUiAction) -> Unit) {
    when {
        state.isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
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

        state.exercisesData.isEmpty() -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                SyncFitText(
                    text = stringResource(R.string.no_exercises_available),
                    textStyle = SyncFitTypography.bodyLarge,
                    textAlign = TextAlign.Center,
                )
            }
        }

        else -> {
            WorkoutsList(state.exercisesData, onAction)
        }
    }
}
