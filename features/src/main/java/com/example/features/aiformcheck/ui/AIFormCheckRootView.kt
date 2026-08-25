package com.example.features.aiformcheck.ui

import androidx.camera.compose.CameraXViewfinder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.features.aiformcheck.viewmodel.AIFormCheckUiAction
import com.example.features.aiformcheck.viewmodel.AIFormCheckViewModel
import com.example.syncfit_core.ui.theme.Spacing

@Composable
fun AIFormCheckRootView(viewModel: AIFormCheckViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(Unit) {
        onDispose {
            viewModel.onAction(AIFormCheckUiAction.EndExercise)
        }
    }

    LaunchedEffect(lifecycleOwner) {
        viewModel.bindToCamera(context, lifecycleOwner)
    }
    Box(Modifier.fillMaxSize()) {
        state.surfaceRequest?.let {
            CameraXViewfinder(it, Modifier.fillMaxSize())
        }
        PoseOverlay(
            pose = state.pose,
            frameInfo = state.frameInfo,
            modifier = Modifier.fillMaxSize(),
        )

        ExerciseStatsOverlay(state.squatResult, state.startCountingReps)

        StartExerciseOverlay(
            Modifier
                .align(Alignment.BottomCenter)
                .padding(bottom = Spacing.xl),
            viewModel::onAction,
        )

    }
}
