package com.example.features.healthconnect

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.syncfit_core.ui.components.SyncFitButton
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitTypography

@Composable
fun HealthPermissionsRationaleScreen(
    onContinue: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(Spacing.md),
        verticalArrangement = Arrangement.spacedBy(Spacing.md),
    ) {
        SyncFitText(
            text = "Health data permissions",
            textStyle = SyncFitTypography.headlineMedium,
        )

        SyncFitText(
            text = "SyncFit uses Health Connect to provide a more useful fitness dashboard.",
        )

        SyncFitText(
            text = "What SyncFit reads",
            textStyle = SyncFitTypography.titleMedium,
        )

        SyncFitText(
            text = """
                • Steps - daily activity progress
                • Heart rate - latest and average heart rate
                • Total calories - daily energy expenditure
                • Sleep sessions - latest sleep duration
            """.trimIndent(),
        )

        SyncFitText(
            text = "What SyncFit writes",
            textStyle = SyncFitTypography.titleMedium,
        )

        SyncFitText(
            text = "SyncFit reads this data only to show your personal health dashboard. You can revoke access at any time in Health Connect settings.",
        )

        SyncFitText(
            text = "You can revoke access at any time from Health Connect settings.",
        )

        SyncFitButton(
            modifier = Modifier.fillMaxWidth(),
            text = "Continue",
            onClick = onContinue,
        )
    }
}
