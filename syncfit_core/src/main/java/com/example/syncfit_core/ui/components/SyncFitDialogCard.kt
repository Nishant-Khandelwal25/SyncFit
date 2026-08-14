package com.example.syncfit_core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitTheme
import com.example.syncfit_core.ui.theme.SyncFitTypography

@Composable
fun SyncFitDialogCard(
    modifier: Modifier = Modifier,
    dialogTitle: String,
    dialogDescription: String,
    dialogConfirmButtonText: String,
    dialogDismissButtonText: String,
    onConfirmButtonClick: () -> Unit,
    onDismissButtonClick: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissButtonClick) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .height(200.dp),
        ) {
            Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                SyncFitText(
                    modifier = Modifier.padding(horizontal = Spacing.md, vertical = Spacing.lg),
                    text = dialogTitle,
                    textStyle = SyncFitTypography.titleLarge,
                )
                SyncFitText(
                    Modifier.padding(horizontal = Spacing.md, vertical = Spacing.xs),
                    text = dialogDescription,
                    textStyle = SyncFitTypography.bodyLarge,
                )
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = Spacing.md, vertical = Spacing.xs),
                    horizontalArrangement = Arrangement.End,
                ) {
                    SyncFitText(
                        modifier = Modifier
                            .padding(vertical = Spacing.md, horizontal = Spacing.xl)
                            .clickable {
                                onDismissButtonClick.invoke()
                            },
                        text = dialogDismissButtonText,
                        textStyle = SyncFitTypography.bodyMedium,
                    )
                    SyncFitText(
                        modifier = Modifier
                            .padding(Spacing.md)
                            .clickable {
                                onConfirmButtonClick.invoke()
                            },
                        text = dialogConfirmButtonText,
                        textStyle = SyncFitTypography.bodyMedium,
                    )
                }
            }
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark_mode")
@Composable
fun SyncFitDialogCardPreview() {
    SyncFitTheme {
        SyncFitDialogCard(
            dialogTitle = "Camera Permission Request",
            dialogDescription = "Please open settings and allow camera permission to start AI Form Check",
            dialogConfirmButtonText = "Settings",
            dialogDismissButtonText = "Dismiss",
            onConfirmButtonClick = {},
        ) { }
    }
}
