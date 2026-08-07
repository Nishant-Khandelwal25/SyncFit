package com.example.syncfit_core.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val SyncFitDarkScheme = darkColorScheme(
    primary = PulseGreen,
    onPrimary = OnPulseGreen,
    primaryContainer = PulseGreenDark,
    onPrimaryContainer = TextPrimaryDark,
    secondary = PulseGreenLight,
    onSecondary = OnPulseGreen,
    background = BackgroundDark,
    onBackground = TextPrimaryDark,
    surface = SurfaceDark,
    onSurface = TextPrimaryDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = TextSecondaryDark,
    error = StatusError,
    onError = OnError,
    errorContainer = StatusErrorContainer,
    outline = BorderDark,
    outlineVariant = BorderDark
)

private val DarkExtendedColors = ExtendedColors(
    success = PulseGreen,
    warning = StatusWarning,
    chartGreen = ChartGreen,
    chartYellow = ChartYellow,
    chartOrange = ChartOrange,
    chartRed = ChartRed,
    border = BorderDark,
    textTertiary = TextTertiaryDark,
    surfaceVariant2 = SurfaceElevatedDark
)

@Composable
fun SyncFitTheme(
    content: @Composable () -> Unit
) {
    val colorScheme = SyncFitDarkScheme
    val extendedColors = DarkExtendedColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
        }
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = SyncFitTypography,
            shapes = SyncFitShapes
        ) {
            Surface(
                color = MaterialTheme.colorScheme.background,
                content = content
            )
        }
    }
}
