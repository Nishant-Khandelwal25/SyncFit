package com.example.features.aiformcheck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import com.example.features.R
import com.example.features.aiformcheck.viewmodel.AIFormCheckUiAction
import com.example.syncfit_core.ui.components.SyncFitResourceImage
import com.example.syncfit_core.ui.theme.OnError
import com.example.syncfit_core.ui.theme.Spacing

@Composable
fun StartExerciseOverlay(modifier: Modifier = Modifier, onAction: (AIFormCheckUiAction) -> Unit) {
    var switch by remember { mutableStateOf(false) }
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(OnError)
            .padding(Spacing.sm),
        contentAlignment = Alignment.Center,
    ) {
        SyncFitResourceImage(
            resId = if (switch) R.drawable.pause_icon else R.drawable.play_icon,
            contentDescription = "Start Exercise",
            modifier = Modifier.clickable {
                switch = !switch
                onAction(AIFormCheckUiAction.StartExercise(shouldStartCountingReps = switch))
            },
        )
    }
}
