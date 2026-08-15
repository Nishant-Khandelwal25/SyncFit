package com.example.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.example.features.R
import com.example.features.home.viewmodel.HomeScreenUiAction
import com.example.syncfit_core.ui.components.SyncFitDialogCard

@Composable
fun CameraPermissionRequestDialog(onAction: (HomeScreenUiAction) -> Unit) {
    SyncFitDialogCard(
        dialogTitle = stringResource(R.string.camera_dialog_title),
        dialogDescription = stringResource(R.string.camera_dialog_description),
        dialogConfirmButtonText = stringResource(R.string.dialog_confirm_button),
        dialogDismissButtonText = stringResource(R.string.dialog_dismiss_button),
        onConfirmButtonClick = { onAction(HomeScreenUiAction.CameraPermissionPositiveButtonClick) },
    ) {
        onAction(HomeScreenUiAction.CameraPermissionDialogDismiss)
    }
}
