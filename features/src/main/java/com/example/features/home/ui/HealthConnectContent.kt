package com.example.features.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.features.R
import com.example.features.home.viewmodel.HomeScreenUiAction
import com.example.features.home.viewmodel.HomeScreenUiState
import com.example.syncfit_core.ui.components.SyncFitButton
import com.example.syncfit_core.ui.components.SyncFitCard
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.Spacing
import kotlin.math.roundToInt

@Composable
fun HealthConnectContent(state: HomeScreenUiState, onAction: (HomeScreenUiAction) -> Unit) {
    when {
        state.isCheckingHealthConnect -> {
            SyncFitCard(
                titleText = stringResource(R.string.status),
                bodyText1 = stringResource(R.string.checking_health_connect),
            )
        }

        !state.isHealthConnectAvailable -> {
            SyncFitCard(
                titleText = stringResource(R.string.status),
                bodyText1 = state.healthError ?: stringResource(R.string.health_connect_unavailable),
            )
        }

        !state.hasHealthPermission -> {
            SyncFitCard(
                titleText = stringResource(R.string.track_your_progress),
                bodyText1 = stringResource(R.string.sync_health_connect),
                buttonText = stringResource(R.string.sync_now),
            ) {
                onAction(HomeScreenUiAction.ConnectHealthConnect)
            }
        }

        state.isLoadingHealthData -> {
            SyncFitCard(
                titleText = stringResource(R.string.status),
                bodyText1 = stringResource(R.string.loading_health_data),
            )
        }

        state.healthSummary != null -> {
            val health = state.healthSummary

            Column(
                Modifier
                    .fillMaxSize()
                    .padding(vertical = Spacing.md),
            ) {
                SyncFitText(
                    modifier = Modifier.padding(horizontal = Spacing.sm, vertical = Spacing.xs),
                    text = stringResource(R.string.health_overview),
                )
                FlowRow(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                    maxItemsInEachRow = 2,
                ) {
                    SyncFitCard(
                        titleText = stringResource(R.string.steps),
                        bodyText1 = "${health.steps}",
                    )
                    SyncFitCard(
                        titleText = stringResource(R.string.total_calories),
                        bodyText1 = "${health.totalCaloriesKcal.toInt()} kcal",
                    )
                    SyncFitCard(
                        titleText = stringResource(R.string.heart_rate),
                        bodyText1 = "${health.heartRateSummary.averageBpm?.roundToInt() ?: "---"} bpm",
                    )
                    SyncFitCard(
                        titleText = stringResource(R.string.latest_sleep),
                        bodyText1 = health.sleepSummary?.durationMinutes?.let { "${it / 60} hours" }
                            ?: "No sleep session found",
                    )
                }

                SyncFitButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(Spacing.sm),
                    text = stringResource(R.string.refresh_health_data),
                    onClick = { onAction(HomeScreenUiAction.RefreshHealthData) },
                )
            }
        }

        else -> SyncFitText(text = state.healthError ?: stringResource(R.string.no_health_data_found))
    }
}
