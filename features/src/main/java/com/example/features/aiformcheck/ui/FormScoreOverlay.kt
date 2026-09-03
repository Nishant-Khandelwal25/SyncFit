package com.example.features.aiformcheck.ui

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.features.R
import com.example.features.aiformcheck.data.enums.SquatPhase
import com.example.features.aiformcheck.data.exercise.SquatResult
import com.example.features.aiformcheck.domain.scoring.ExerciseFormScore
import com.example.features.aiformcheck.util.ExerciseStatsUtil.getFormScoreFeedback
import com.example.features.aiformcheck.viewmodel.AIFormCheckUiAction
import com.example.syncfit_core.ui.components.SyncFitCard
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitShapes
import com.example.syncfit_core.ui.theme.SyncFitTheme
import com.example.syncfit_core.ui.theme.SyncFitTypography
import com.example.syncfit_core.ui.theme.TextPrimaryDark
import com.example.syncfit_core.ui.theme.TextSecondaryDark
import com.example.syncfit_core.ui.theme.TextTertiaryDark

@Composable
fun FormScoreOverlay(
    modifier: Modifier = Modifier,
    squatResult: SquatResult?,
    onAction: (AIFormCheckUiAction) -> Unit,
) {
    val scoreToDisplay = squatResult?.lastRepFormScore?.overallScore
    val scoreFeedback = if (scoreToDisplay != null) stringResource(getFormScoreFeedback(scoreToDisplay)) else ""
    val feedback = squatResult?.lastRepFormScore?.feedback?.firstOrNull()
    Column(modifier.fillMaxWidth()) {
        SyncFitCard(
            modifier = Modifier
                .width(144.dp)
                .padding(vertical = Spacing.sm),
            titleText = stringResource(R.string.form_score),
            bodyText1 = scoreToDisplay?.toString() ?: "---",
            bodyText2 = scoreFeedback,
            titleTextStyle = SyncFitTypography.bodySmall,
            bodyText1Style = SyncFitTypography.headlineMedium,
            bodyText2Style = SyncFitTypography.bodyMedium,
            titleColor = TextSecondaryDark,
            bodyText1Color = TextPrimaryDark,
        )

        feedback?.let {
            SyncFitText(
                text = it,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.sm)
                    .clip(SyncFitShapes.small)
                    .background(TextTertiaryDark)
                    .padding(Spacing.sm),
                textAlign = TextAlign.Center,
            )
        }

        StartExerciseOverlay(
            Modifier
                .align(Alignment.CenterHorizontally)
                .padding(vertical = Spacing.xl),
            onAction,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark_mode")

@Composable
private fun FormScoreOverlayPreview() {
    val squatResult = SquatResult(
        reps = 10,
        phase = SquatPhase.STANDING,
        kneeAngle = null,
        leftKneeAngle = null,
        rightKneeAngle = null,
        isValidPose = true,
        lastRepFormScore = ExerciseFormScore(
            overallScore = 90,
            feedback = listOf("Keep your back straight."),
            metricScores = emptyList(),
            confidence = 0.8f,
            durationMillis = 2919L,
        ),
    )
    SyncFitTheme {
        FormScoreOverlay(squatResult = squatResult) {}
    }
}
