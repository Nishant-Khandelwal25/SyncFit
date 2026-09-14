package com.example.features.workouts.ui

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.features.R
import com.example.features.workouts.model.ExerciseUiInfo
import com.example.features.workouts.viewmodel.WorkoutsUiAction
import com.example.syncfit_core.room.model.ExerciseType
import com.example.syncfit_core.ui.components.SyncFitCard
import com.example.syncfit_core.ui.theme.IconSize
import com.example.syncfit_core.ui.theme.IconSize.xxxxl
import com.example.syncfit_core.ui.theme.Spacing.xxxs
import com.example.syncfit_core.ui.theme.SyncFitTheme

@Composable
fun WorkoutItem(exerciseInfo: ExerciseUiInfo, onAction: (WorkoutsUiAction) -> Unit) {
    SyncFitCard(
        titleText = exerciseInfo.exerciseName,
        bodyText1 = exerciseInfo.bodyPart,
        startIconResId = exerciseInfo.resId,
        startIconSize = xxxxl,
        startIconTopPadding = 0.dp,
        startIconStartPadding = xxxs,
        endIconResId = R.drawable.arrow_right,
        endIconAlignment = Alignment.CenterVertically,
        endIconTopPadding = 0.dp,
        endIconSize = IconSize.sm,
    ) {
        onAction(WorkoutsUiAction.OnWorkoutClick(exerciseInfo.exerciseName, exerciseInfo.exerciseType.name))
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark_mode")
@Composable
private fun WorkoutItemPreview() {
    SyncFitTheme {
        WorkoutItem(
            ExerciseUiInfo(
                "Barbell Squats",
                "Legs",
                R.drawable.barbell_squat,
                ExerciseType.SQUAT,
            ),
        ) {}
    }
}
