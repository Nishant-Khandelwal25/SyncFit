package com.example.features.workouthistory.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.features.R
import com.example.features.workouthistory.util.WorkoutHistoryUtil.toFormatDateAndTime
import com.example.syncfit_core.room.entity.ExerciseSession
import com.example.syncfit_core.room.model.ExerciseType
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitTheme
import com.example.syncfit_core.ui.theme.SyncFitTypography
import com.example.syncfit_core.ui.theme.TextSecondaryDark

@Composable
fun WorkoutSessionItem(session: ExerciseSession) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(Spacing.md), verticalArrangement = Arrangement.spacedBy(Spacing.sm)) {
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

            //todo Add the score in future once score algo is in place
        }
    }
}

@Composable
fun RepsSetsCount(session: ExerciseSession) {
    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(Spacing.xs)) {
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
    )
    SyncFitTheme {
        WorkoutSessionItem(session)
    }
}
