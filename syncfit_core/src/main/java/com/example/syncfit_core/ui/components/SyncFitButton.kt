package com.example.syncfit_core.ui.components

import android.content.res.Configuration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.syncfit_core.ui.theme.BackgroundDark
import com.example.syncfit_core.ui.theme.ChartGreen
import com.example.syncfit_core.ui.theme.SyncFitShapes

@Composable
fun SyncFitButton(
    modifier: Modifier = Modifier,
    text: String,
    buttonColor: Color = ChartGreen,
    textColor: Color = BackgroundDark,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(buttonColor, contentColor = textColor),
        shape = SyncFitShapes.medium,
        modifier = modifier
    ) {
        Text(text = text)
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark_mode")
@Composable
fun SyncFitButtonPreview() {
    SyncFitButton(text = "Sample Text") {}
}
