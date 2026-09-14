package com.example.syncfit_core.ui.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource

@Composable
fun SyncFitResourceImage(
    modifier: Modifier = Modifier,
    resId: Int,
    contentDescription: String = "",
    contentScale: ContentScale = ContentScale.Fit,
) {
    Image(
        painter = painterResource(resId),
        contentDescription = contentDescription,
        modifier = modifier,
        contentScale = contentScale,
    )
}
