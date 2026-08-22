package com.example.features.aiformcheck.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import com.example.features.aiformcheck.data.exercise.SquatResult
import com.example.features.aiformcheck.util.ExerciseStatsUtil.formatTime
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.BorderDark
import com.example.syncfit_core.ui.theme.BorderWidth
import com.example.syncfit_core.ui.theme.ChartGreen
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitShapes
import com.example.syncfit_core.ui.theme.SyncFitTypography
import kotlinx.coroutines.delay

@Composable
fun ExerciseStatsOverlay(squatResult: SquatResult?, startCountingReps: Boolean) {
    var timeInMillis by remember { mutableLongStateOf(0L) }

    LaunchedEffect(startCountingReps) {
        if (startCountingReps) {
            val startTime = System.currentTimeMillis() - timeInMillis
            while (true) {
                timeInMillis = System.currentTimeMillis() - startTime
                delay(10L)
            }
        }
    }

    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = Spacing.md, vertical = Spacing.xl),
    ) {
        SyncFitText(
            text = timeInMillis.formatTime(),
            modifier = Modifier
                .clip(SyncFitShapes.medium)
                .background(BorderDark)
                .padding(Spacing.sm),
            textStyle = SyncFitTypography.headlineMedium,
        )
        Box(
            modifier = Modifier
                .clip(CircleShape)
                .border(BorderWidth.lg, ChartGreen, shape = CircleShape)
                .background(BorderDark)
                .padding(Spacing.sm),
        ) {
            SyncFitText(
                modifier = Modifier.padding(Spacing.sm),
                text = "${squatResult?.reps ?: 0}\n Reps",
                textStyle = SyncFitTypography.headlineMedium,
                textAlign = TextAlign.Center,
            )
        }

    }
}
