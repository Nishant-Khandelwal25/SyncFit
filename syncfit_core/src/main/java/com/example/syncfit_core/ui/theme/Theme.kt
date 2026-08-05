package com.example.syncfit_core.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val PulseSyncDarkScheme = darkColorScheme(
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

// Kept for a future light-mode / accessibility toggle — the shipped design is dark-first.
private val PulseSyncLightScheme = lightColorScheme(
    primary = PulseGreenDark,
    onPrimary = Color.White,
    background = Color(0xFFF7F9FC),
    surface = Color.White,
    onSurface = Color(0xFF0B1220),
    error = StatusError,
    onError = Color.White
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

private val LightExtendedColors = DarkExtendedColors.copy(
    border = Color(0xFFE2E8F0),
    textTertiary = Color(0xFF64748B),
    surfaceVariant2 = Color(0xFFF1F5F9)
)

@Composable
fun SyncFitTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) PulseSyncDarkScheme else PulseSyncLightScheme
    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = PulseSyncTypography,
            shapes = SyncFitShapes,
            content = content
        )
    }
}

/** Access custom tokens not on Material3's ColorScheme, e.g. MaterialTheme.extendedColors.success */
val MaterialTheme.extendedColors: ExtendedColors
    @Composable
    get() = LocalExtendedColors.current