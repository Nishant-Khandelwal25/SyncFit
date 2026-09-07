package com.example.features.login.ui

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.features.R
import com.example.features.login.viewmodel.LoginUiAction
import com.example.syncfit_core.ui.components.SyncFitButton
import com.example.syncfit_core.ui.components.SyncFitResourceImage
import com.example.syncfit_core.ui.components.SyncFitText
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitTheme

@Composable
fun LoginView(onAction: (LoginUiAction) -> Unit) {
    var username by rememberSaveable { mutableStateOf("") }
    Column(
        Modifier
            .fillMaxSize()
            .padding(Spacing.md),
        verticalArrangement = Arrangement.Center,
    ) {
        SyncFitResourceImage(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .padding(vertical = Spacing.md)
                .align(Alignment.CenterHorizontally),
            resId = R.drawable.sync_fit_logo,
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = username,
            onValueChange = { username = it },
            label = { SyncFitText(text = stringResource(R.string.username)) },
            placeholder = { SyncFitText(text = stringResource(R.string.enter_username)) },
        )

        SyncFitButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = Spacing.md),
            text = stringResource(R.string.login),
            enabled = username.isNotBlank(),
        ) {
            onAction(LoginUiAction.Login(username))
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark_mode")
@Composable
private fun LoginViewPreview() {
    SyncFitTheme {
        LoginView {}
    }
}
