package com.example.features.home.ui

import android.Manifest
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.features.R
import com.example.features.home.model.HomeScreenData
import com.example.features.home.viewmodel.HomeScreenUiAction
import com.example.features.home.viewmodel.HomeScreenUiState
import com.example.syncfit_core.ui.components.SyncFitCard
import com.example.syncfit_core.ui.components.SyncFitResourceImage
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.IconSize
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitTheme
import com.example.syncfit_core.ui.theme.SyncFitTypography
import com.example.syncfit_core.ui.theme.TextPrimaryDark
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale

@Composable
fun HomeScreenContent(state: HomeScreenUiState, onAction: (HomeScreenUiAction) -> Unit) {
    LazyColumn(
        Modifier
            .fillMaxSize()
            .padding(Spacing.sm),
    ) {
        item { HomeScreenHeader() }
        item { HomeScreenRecoverySection(state.recoveryScore) }
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(Spacing.xs),
            ) {
                state.healthConnectFeatures.forEach {
                    HomeScreenSleepAndHrvSection(modifier = Modifier.weight(1f), it)

                }
            }
        }
        item {
            HomeScreenStartWorkOutSection(
                state.startWorkout,
                state.cameraPermissionRequested,
                state.launchSettingsForCameraPermission,
                onAction,
            )
        }

        item { HomeScreenQuickInsights(state.quickInsights) }
    }
}


@Composable
fun HomeScreenHeader() {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(Spacing.sm),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        SyncFitText(text = stringResource(R.string.hi_name, "Nishant"), textStyle = SyncFitTypography.titleLarge)
        SyncFitResourceImage(resId = R.drawable.wave_hand, modifier = Modifier.padding(horizontal = Spacing.xs))
    }
}

@Composable
fun HomeScreenRecoverySection(recoveryScore: HomeScreenData) {
    SyncFitCard(
        titleText = recoveryScore.featureName,
        bodyText1 = recoveryScore.featureValue,
        bodyText2 = recoveryScore.featureStatus,
        titleTextStyle = SyncFitTypography.bodyLarge,
        bodyText1Style = SyncFitTypography.displayLarge,
        endIconResId = R.drawable.incline_graph,
        endIconSize = 96.dp,
        bodyText3 = recoveryScore.changeInValue,
        bodyText1Color = TextPrimaryDark,
    )
}

@Composable
fun HomeScreenSleepAndHrvSection(
    modifier: Modifier = Modifier,
    homeScreenData: HomeScreenData,
) {
    SyncFitCard(
        modifier = modifier,
        titleText = homeScreenData.featureName,
        bodyText1 = homeScreenData.featureValue,
        bodyText2 = homeScreenData.featureStatus,
        titleTextStyle = SyncFitTypography.bodyLarge,
        bodyText1Style = SyncFitTypography.titleLarge,
        endIconResId = R.drawable.arrow_right,
        endIconSize = IconSize.sm,
        bodyText1Color = TextPrimaryDark,
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun HomeScreenStartWorkOutSection(
    homeScreenData: HomeScreenData,
    cameraPermissionRequested: Boolean,
    launchSettings: Boolean,
    onAction: (HomeScreenUiAction) -> Unit,
) {
    val cameraPermissionState = rememberPermissionState(Manifest.permission.CAMERA)
    val context = LocalContext.current

    val settingsLauncher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    if (launchSettings) {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", context.packageName, null)
        }
        onAction(HomeScreenUiAction.SettingsLaunched)
        onAction(HomeScreenUiAction.CameraPermissionDialogDismiss)
        settingsLauncher.launch(intent)
    }

    Column(
        Modifier
            .fillMaxSize()
            .padding(vertical = Spacing.md),
    ) {
        SyncFitText(
            modifier = Modifier.padding(horizontal = Spacing.sm, vertical = Spacing.xs),
            text = stringResource(R.string.today_plan),
        )
        SyncFitCard(
            titleText = homeScreenData.featureName,
            bodyText1 = homeScreenData.featureValue,
            endIconResId = R.drawable.arrow_right,
            buttonText = homeScreenData.buttonText,
            endIconSize = IconSize.sm,
        ) {
            when {
                cameraPermissionState.status.isGranted -> {
                    onAction(HomeScreenUiAction.OnStartAIFormCheckClick)
                }

                !cameraPermissionState.status.shouldShowRationale && cameraPermissionRequested -> {
                    onAction(HomeScreenUiAction.LaunchCameraPermissionDialog)
                }

                else -> {
                    onAction(HomeScreenUiAction.CameraPermissionRequested)
                    cameraPermissionState.launchPermissionRequest()

                }

            }
        }
    }
}

@Composable
fun HomeScreenQuickInsights(homeScreenData: HomeScreenData) {
    Column(Modifier.fillMaxSize()) {
        SyncFitText(
            modifier = Modifier.padding(horizontal = Spacing.sm, vertical = Spacing.xs),
            text = stringResource(R.string.quick_insights),
        )
        SyncFitCard(
            titleText = homeScreenData.featureName,
            bodyText1 = homeScreenData.featureValue,
            startIconResId = R.drawable.thought,
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark_mode")
@Composable
fun HomeScreenContentPreview() {
    SyncFitTheme {
        HomeScreenContent(
            state = HomeScreenUiState(
                listOf(
                    HomeScreenData("Sleep", "7h 45m", "Good"),
                    HomeScreenData("HRV", "58ms", "Good"),
                ),
                HomeScreenData("Recovery Score", "82", "Good", changeInValue = "12 points higher vs yesterday"),
                HomeScreenData(
                    "Upper Body Strength",
                    featureValue = "45 mins - 6 exercises",
                    buttonText = "Start Workout",
                ),
                HomeScreenData("Recovery is looking good", "You're ready for a normal intensity workout today."),
            ),
        ) {}
    }
}
