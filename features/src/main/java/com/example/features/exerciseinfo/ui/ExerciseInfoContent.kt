package com.example.features.exerciseinfo.ui

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.features.R
import com.example.features.exerciseinfo.viewmodel.ExerciseInfoUiAction
import com.example.syncfit_core.api.model.result.ExerciseInfoResult
import com.example.syncfit_core.ui.components.SyncFitButton
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.BorderDark
import com.example.syncfit_core.ui.theme.ChartGreen
import com.example.syncfit_core.ui.theme.ChartRed
import com.example.syncfit_core.ui.theme.ChartYellow
import com.example.syncfit_core.ui.theme.PulseGreen
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitShapes
import com.example.syncfit_core.ui.theme.SyncFitTheme
import com.example.syncfit_core.ui.theme.SyncFitTypography
import com.example.syncfit_core.ui.theme.TextPrimaryDark

@Composable
fun ExerciseInfoContent(
    exerciseInfoResult: ExerciseInfoResult?,
    @DrawableRes resId: Int,
    onAction: (ExerciseInfoUiAction) -> Unit,
) {
    val exercise = exerciseInfoResult ?: return

    Column(modifier = Modifier.fillMaxWidth()) {
        ExerciseHeroImage(
            resId = resId,
            contentDescription = exercise.exerciseName,
        )

        Spacer(Modifier.height(Spacing.lg))

        ExerciseMetaData(
            muscleGroups = exercise.targetedMuscleGroup,
            equipment = exercise.equipment,
            level = exercise.exerciseLevel,
        )

        Spacer(Modifier.height(Spacing.lg))

        HowToPerformSection(steps = exercise.howToPerform)

        Spacer(Modifier.height(Spacing.lg))

        DemonstrationCard(
            demonstrationLink = exercise.demonstrationLink,
            onClick = {
                onAction(
                    ExerciseInfoUiAction.WatchDemonstration(
                        exercise.demonstrationLink,
                    ),
                )
            },
        )

        Spacer(Modifier.height(Spacing.md))

        SyncFitButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            text = "Start AI Form Check",
            enabled = exercise.isAiFormCheckEnabled,
            onClick = {
                onAction(
                    ExerciseInfoUiAction.StartAiFormCheck(
                        exerciseName = exercise.exerciseName,
                    ),
                )
            },
        )

        Spacer(Modifier.height(Spacing.lg))
    }
}

@Composable
private fun ExerciseHeroImage(
    @DrawableRes resId: Int,
    contentDescription: String,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(240.dp),
        shape = SyncFitShapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
    ) {
        if (resId != 0) {
            Image(
                painter = painterResource(resId),
                contentDescription = contentDescription,
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillBounds,
            )
        }
    }
}

@Composable
private fun ExerciseMetaData(
    muscleGroups: List<String>,
    equipment: String,
    level: String,
) {
    val levelColors = getExerciseLevelColors(level)
    Column(verticalArrangement = Arrangement.spacedBy(Spacing.md)) {
        ExerciseInfoRow(
            label = "Target muscles",
            value = muscleGroups.joinToString(),
        )

        ExerciseInfoRow(
            label = "Equipment",
            value = equipment,
            trailingContent = {
                Surface(
                    color = levelColors.background,
                    contentColor = MaterialTheme.colorScheme.primary,
                    shape = SyncFitShapes.small,
                    modifier = Modifier
                        .border(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.primary.copy(alpha = 0.5f),
                            shape = SyncFitShapes.small,
                        )
                        .padding(Spacing.xxs),
                ) {
                    SyncFitText(
                        text = level,
                        textStyle = SyncFitTypography.labelLarge,
                        textColor = levelColors.content,
                        modifier = Modifier.padding(
                            horizontal = Spacing.sm,
                            vertical = Spacing.xs,
                        ),
                    )
                }
            },
        )
    }
}

@Composable
private fun ExerciseInfoRow(
    label: String,
    value: String,
    trailingContent: @Composable (() -> Unit)? = null,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(modifier = Modifier.weight(1f)) {
            SyncFitText(
                text = label,
                textStyle = SyncFitTypography.labelMedium,
                textColor = MaterialTheme.colorScheme.onSurfaceVariant,
            )

            Spacer(Modifier.height(Spacing.xxs))

            SyncFitText(
                text = value,
                textStyle = SyncFitTypography.bodyLarge,
            )
        }

        trailingContent?.let {
            Spacer(Modifier.width(Spacing.sm))
            it()
        }
    }
}

@Composable
private fun HowToPerformSection(steps: List<String>) {
    Column {
        SyncFitText(
            text = "How to perform",
            textStyle = SyncFitTypography.titleLarge.copy(
                fontWeight = FontWeight.SemiBold,
            ),
        )

        Spacer(Modifier.height(Spacing.md))

        steps.forEachIndexed { index, step ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = Spacing.sm),
                verticalAlignment = Alignment.Top,
            ) {
                Box(
                    modifier = Modifier
                        .size(28.dp)
                        .clip(CircleShape)
                        .background(PulseGreen),
                    contentAlignment = Alignment.Center,
                ) {
                    SyncFitText(
                        text = (index + 1).toString(),
                        textStyle = SyncFitTypography.labelLarge,
                        textColor = Color(0xFF04140D),
                    )
                }

                Spacer(Modifier.width(Spacing.sm))

                SyncFitText(
                    modifier = Modifier.padding(top = 3.dp),
                    text = step,
                    textStyle = SyncFitTypography.bodyLarge,
                )
            }
        }
    }
}

@Composable
private fun DemonstrationCard(
    demonstrationLink: String,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                enabled = demonstrationLink.isNotBlank(),
                onClick = onClick,
            ),
        shape = SyncFitShapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = CardDefaults.outlinedCardBorder().copy(
            brush = androidx.compose.ui.graphics.SolidColor(BorderDark),
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(Spacing.md),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Image(
                painter = painterResource(R.drawable.play_icon),
                contentDescription = "Watch demonstration",
                modifier = Modifier.size(40.dp),
            )

            Spacer(Modifier.width(Spacing.md))

            Column(modifier = Modifier.weight(1f)) {
                SyncFitText(
                    text = "Watch demonstration",
                    textStyle = SyncFitTypography.titleMedium,
                )

                Spacer(Modifier.height(Spacing.xxs))

                SyncFitText(
                    text = if (demonstrationLink.isBlank()) {
                        "Demonstration unavailable"
                    } else {
                        "Video demonstration"
                    },
                    textStyle = SyncFitTypography.bodyMedium,
                    textColor = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }
        }
    }
}

private data class LevelColors(
    val background: Color,
    val content: Color,
)

private fun getExerciseLevelColors(level: String): LevelColors {
    return when (level.trim().lowercase()) {
        "beginner" -> LevelColors(
            background = ChartGreen.copy(alpha = 0.18f),
            content = ChartGreen,
        )

        "intermediate" -> LevelColors(
            background = ChartYellow.copy(alpha = 0.18f),
            content = ChartYellow,
        )

        "advanced" -> LevelColors(
            background = ChartRed.copy(alpha = 0.18f),
            content = ChartRed,
        )

        else -> LevelColors(
            background = Color.White.copy(alpha = 0.12f),
            content = TextPrimaryDark,
        )
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark_mode")
@Composable
private fun ExerciseInfoContentPreview() {
    SyncFitTheme {
        ExerciseInfoContent(
            exerciseInfoResult = ExerciseInfoResult(
                id = 1,
                exerciseName = "Squat",
                exerciseVariations = listOf(
                    "Bodyweight Squat",
                    "Barbell Back Squat",
                    "Barbell Front Squat",
                    "Goblet Squat",
                    "Dumbbell Squat",
                    "Smith Machine Squat",
                    "Hack Squat",
                ),
                primaryMuscle = "Quadriceps",
                targetedMuscleGroup = listOf("Quadriceps", "Glutes", "Hamstrings", "Adductors"),
                secondaryMuscle = "Core and lower back",
                equipment = "Barbell and squat rack",
                howToPerform = listOf(
                    "Position the bar across your upper back and stand with your feet approximately shoulder-width apart.",
                    "Brace your core, keep your chest up, and maintain a neutral spine.",
                    "Bend your hips and knees together, keeping your knees aligned with your toes.",
                    "Lower under control until your thighs are at least parallel to the floor, or as mobility allows.",
                    "Drive through your whole foot and extend your hips and knees to return to standing.",
                ),
                exerciseLevel = "Advanced",
                isAiFormCheckEnabled = true,
                demonstrationLink = "",
            ),
            resId = R.drawable.barbell_squat,
        ) {}
    }
}
