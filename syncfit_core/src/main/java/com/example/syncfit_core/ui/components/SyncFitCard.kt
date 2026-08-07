package com.example.syncfit_core.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.syncfit_core.R
import com.example.syncfit_core.ui.theme.ChartGreen
import com.example.syncfit_core.ui.theme.IconSize
import com.example.syncfit_core.ui.theme.SyncFitTypography
import com.example.syncfit_core.ui.theme.Spacing
import com.example.syncfit_core.ui.theme.SyncFitShapes
import com.example.syncfit_core.ui.theme.SyncFitTheme
import com.example.syncfit_core.ui.theme.TextPrimaryDark
import com.example.syncfit_core.ui.theme.TextSecondaryDark

val defaultCardPaddingValues =
    PaddingValues(top = Spacing.md, bottom = Spacing.md, start = Spacing.md, end = Spacing.md)

@Composable
fun SyncFitCard(
    modifier: Modifier = Modifier,
    titleText: String,
    bodyText1: String,
    bodyText2: String? = null,
    buttonText: String? = null,
    startIconResId: Int? = null,
    endIconResId: Int? = null,
    cardShape: Shape = SyncFitShapes.medium,
    startIconSize: Dp = IconSize.lg,
    endIconSize: Dp = IconSize.lg,
    titleTextStyle: TextStyle = SyncFitTypography.titleLarge,
    bodyText1Style: TextStyle = SyncFitTypography.bodyLarge,
    bodyText2Style: TextStyle = SyncFitTypography.bodyLarge,
    titleColor: Color = TextPrimaryDark,
    bodyText1Color: Color = TextSecondaryDark,
    bodyText2Color: Color = ChartGreen,
    onCardClick: (() -> Unit)? = null,
    onButtonClick: (() -> Unit)? = null,
) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(defaultCardPaddingValues)
            .clip(cardShape)
            .clickable { onCardClick?.invoke() },
        verticalAlignment = Alignment.Top,
    ) {
        startIconResId?.let {
            SyncFitResourceImage(
                resId = it,
                modifier = Modifier
                    .padding(top = Spacing.md, start = Spacing.md)
                    .size(startIconSize)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = Spacing.md, start = Spacing.md)
                .fillMaxWidth()
                .weight(1f)
        ) {
            SyncFitText(text = titleText, textStyle = titleTextStyle, textColor = titleColor)

            SyncFitText(
                modifier = Modifier.padding(vertical = Spacing.sm),
                text = bodyText1,
                textStyle = bodyText1Style,
                textColor = bodyText1Color,
            )

            bodyText2?.let { SyncFitText(text = it, textStyle = bodyText2Style, textColor = bodyText2Color) }

            buttonText?.let {
                SyncFitButton(
                    text = it, modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = Spacing.md)
                ) {
                    onButtonClick?.invoke()
                }
            }
        }

        endIconResId?.let {
            SyncFitResourceImage(
                resId = it,
                modifier = Modifier
                    .padding(top = Spacing.md, end = Spacing.md)
                    .size(endIconSize)
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, name = "dark_mode")
@Composable
fun SyncFitCardPreview() {
    SyncFitTheme {
        SyncFitCard(
            titleText = "Some Title",
            bodyText1 = "Body Text 1",
            startIconResId = R.drawable.sample_account_vector,
            endIconResId = R.drawable.sample_account_vector,
            bodyText2 = "Body Text 2",
            buttonText = "Some Button Text"
        )
    }
}
