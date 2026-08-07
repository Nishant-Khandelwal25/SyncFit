package com.example.syncfit_core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.syncfit_core.ui.theme.IconSize

@Composable
fun SyncFitResourceImage(modifier: Modifier = Modifier, resId: Int, contentDescription: String = "") {
    Image(
        painter = painterResource(resId),
        contentDescription = contentDescription,
        modifier = modifier.size(IconSize.md),
        contentScale = ContentScale.Fit,
    )
}
