package com.example.features.exerciseinfo.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.features.exerciseinfo.viewmodel.ExerciseInfoUiAction
import com.example.features.exerciseinfo.viewmodel.ExerciseInfoUiState
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitTypography

@Composable
fun ExerciseInfoScreen(modifier: Modifier, state: ExerciseInfoUiState, onAction: (ExerciseInfoUiAction) -> Unit) {
    LazyColumn(modifier.fillMaxSize(), contentPadding = PaddingValues(Spacing.md)) {
        when {
            state.loading -> {
                item {
                    Box(Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                        CircularProgressIndicator()
                    }
                }
            }

            state.errorMessage != null -> {
                item {
                    Box(Modifier.fillParentMaxSize(), contentAlignment = Alignment.Center) {
                        SyncFitText(
                            text = state.errorMessage,
                            textStyle = SyncFitTypography.bodyLarge,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

            else -> {
                item { ExerciseInfoContent(state.exerciseInfoResult, state.resId, onAction) }
            }
        }
    }
}
