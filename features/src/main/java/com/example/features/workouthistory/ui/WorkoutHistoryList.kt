package com.example.features.workouthistory.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.syncfit_core.room.entity.ExerciseSession
import com.example.syncfit_core.room.model.ExerciseType
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitTheme

@Composable
fun WorkoutHistoryList(exerciseSession: List<ExerciseSession>) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(vertical = Spacing.md),
        contentPadding = PaddingValues(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        items(items = exerciseSession, key = { session -> session.id }) { session ->
            WorkoutSessionItem(session)
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark_mode")
@Composable
private fun WorkoutHistoryListPreview() {
    val session = listOf(
        ExerciseSession(
            id = 1,
            exerciseType = ExerciseType.SQUAT,
            repsCount = 20,
            setsCount = 2,
            startedAt = 1788185120139,
            endedAt = 1788185120170,
            formScore = 80,
        ),
        ExerciseSession(
            id = 2,
            exerciseType = ExerciseType.SQUAT,
            repsCount = 12,
            setsCount = 2,
            startedAt = 1788185120139,
            endedAt = 1788185120170,
            formScore = 85,
        ),
        ExerciseSession(
            id = 3,
            exerciseType = ExerciseType.SQUAT,
            repsCount = 30,
            setsCount = 3,
            startedAt = 1788185120139,
            endedAt = 1788185120170,
            formScore = 90,
        ),
    )
    SyncFitTheme {
        WorkoutHistoryList(session)
    }
}
