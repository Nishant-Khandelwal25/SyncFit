package com.example.syncfit_core.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

/**
 * Material3's ColorScheme has no slots for "success", chart gradients, or a
 * distinct secondary border/text-tertiary tone — this fills that gap.
 * Access via MaterialTheme.extendedColors (see Theme.kt).
 */
data class ExtendedColors(
    val success: Color,
    val warning: Color,
    val chartGreen: Color,
    val chartYellow: Color,
    val chartOrange: Color,
    val chartRed: Color,
    val border: Color,
    val textTertiary: Color,
    val surfaceVariant2: Color
)

// Fallback only — PulseSyncTheme always provides a real value explicitly.
val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
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
}