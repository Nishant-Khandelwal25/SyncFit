package com.example.features.workouthistory.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.features.R
import com.example.features.workouthistory.util.WorkoutHistoryUtil.toFormatDateAndTime
import com.example.syncfit_core.room.entity.ExerciseSession
import com.example.syncfit_core.room.model.ExerciseType
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.BorderDark
import com.example.syncfit_core.ui.theme.BorderWidth
import com.example.syncfit_core.ui.theme.ChartGreen
import com.example.syncfit_core.ui.theme.PulseGreenLight
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitTheme
import com.example.syncfit_core.ui.theme.SyncFitTypography
import com.example.syncfit_core.ui.theme.TextSecondaryDark

@Composable
fun WorkoutSessionItem(session: ExerciseSession) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = Spacing.md),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            ExerciseStatsColumn(session)
            ExerciseFormScore(session)
        }
    }
}

@Composable
fun ExerciseStatsColumn(session: ExerciseSession) {
    Column(Modifier.padding(vertical = Spacing.md), verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
        SyncFitText(
            text = session.startedAt?.toFormatDateAndTime().orEmpty(),
            textStyle = SyncFitTypography.bodyMedium,
            textColor = TextSecondaryDark,
        )

        SyncFitText(
            text = session.exerciseType.name,
            textStyle = SyncFitTypography.titleLarge,
        )

        RepsSetsCount(session)
    }
}

@Composable
fun ExerciseFormScore(session: ExerciseSession) {
    Box(
        modifier = Modifier
            .clip(CircleShape)
            .border(BorderWidth.sm, PulseGreenLight, shape = CircleShape)
            .background(BorderDark)
            .padding(Spacing.sm),
    ) {
        SyncFitText(
            Modifier.padding(Spacing.xs),
            text = session.formScore?.toString() ?: "---",
            textStyle = SyncFitTypography.headlineMedium,
            textAlign = TextAlign.Center,
            textColor = ChartGreen,
        )
    }
}

@Composable
fun RepsSetsCount(session: ExerciseSession) {
    Row(horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
        SyncFitText(
            text = session.setsCount?.let { stringResource(R.string.sets_count, it) }.orEmpty(),
            textStyle = SyncFitTypography.bodyMedium,
        )
        SyncFitText(text = " - ")
        SyncFitText(
            text = session.repsCount?.let { stringResource(R.string.reps_count, it) }.orEmpty(),

            textStyle = SyncFitTypography.bodyMedium,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark_mode")
@Composable
private fun WorkoutSessionItemPreview() {
    val session = ExerciseSession(
        exerciseType = ExerciseType.SQUAT,
        repsCount = 20,
        setsCount = 2,
        startedAt = 1788185120139,
        endedAt = 1788185120170,
        formScore = 80,
    )
    SyncFitTheme {
        WorkoutSessionItem(session)
    }
}
