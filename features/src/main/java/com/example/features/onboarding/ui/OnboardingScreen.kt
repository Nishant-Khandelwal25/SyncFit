package com.example.features.onboarding.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.features.R
import com.example.features.onboarding.viewmodel.OnboardingUiAction
import com.example.syncfit_core.ui.components.SyncFitButton
import com.example.syncfit_core.ui.components.SyncFitResourceImage
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.ChartGreen
import com.example.syncfit_core.ui.theme.IconSize
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitTheme
import com.example.syncfit_core.ui.theme.SyncFitTypography

@Composable
fun OnboardingScreen(
    onAction: (OnboardingUiAction) -> Unit,
) {
    val infoItems = stringArrayResource(R.array.onboarding_items)
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item { OnboardingHeaderSection() }
        item {
            Column(
                modifier = Modifier.padding(vertical = Spacing.md),
                horizontalAlignment = Alignment.Start,
            ) {
                infoItems.forEach { item -> OnboardingInfoItems(item) }
            }
        }
        item {
            SyncFitButton(
                text = stringResource(R.string.get_started_button),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(Spacing.xl),
            ) {
                onAction(OnboardingUiAction.GetStarted)
            }
        }
    }
}

@Composable
fun OnboardingHeaderSection() {
    SyncFitText(
        text = stringResource(R.string.welcome_to),
        textStyle = SyncFitTypography.displayLarge,
        textAlign = TextAlign.Center,
    )
    SyncFitText(
        text = stringResource(R.string.syncfit),
        textStyle = SyncFitTypography.displayLarge,
        textColor = ChartGreen,
        textAlign = TextAlign.Center,
    )
    SyncFitText(
        text = stringResource(R.string.onboarding_description),
        modifier = Modifier.padding(vertical = Spacing.lg),
        textAlign = TextAlign.Center,
    )

    SyncFitResourceImage(resId = R.drawable.onboarding_banner)
}

@Composable
fun OnboardingInfoItems(item: String) {
    Row(
        modifier = Modifier
            .padding(horizontal = Spacing.xl, vertical = Spacing.sm),
        verticalAlignment = Alignment.Top,
    ) {
        SyncFitResourceImage(resId = R.drawable.pulsesync_checkmark, modifier = Modifier.size(IconSize.md))
        SyncFitText(
            text = item,
            modifier = Modifier
                .padding(horizontal = Spacing.sm)
                .weight(1f, fill = false),
            textAlign = TextAlign.Center,
        )
    }
}

@Preview(showSystemUi = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark_mode")
@Composable
fun OnboardingScreenPreview() {
    SyncFitTheme {
        OnboardingScreen(onAction = {})
    }
}
