package com.example.syncfit_core.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.example.syncfit_core.ui.theme.SyncFitTypography
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitTheme
import com.example.syncfit_core.ui.theme.TextPrimaryDark

@Composable
fun SyncFitText(
    modifier: Modifier = Modifier,
    text: String,
    textStyle: TextStyle = SyncFitTypography.bodyLarge,
    textColor: Color = TextPrimaryDark,
    maxLines: Int = Int.MAX_VALUE,
    textAlign: TextAlign? = null,
) {
    Text(
        text = text,
        modifier = modifier,
        style = textStyle,
        color = textColor,
        textAlign = textAlign,
        maxLines = maxLines,
    )
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SyncFitTextPreview() {
    SyncFitTheme {
        SyncFitText(text = "Sample Text", modifier = Modifier.padding(Spacing.md))
    }
}
