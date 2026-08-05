package com.example.syncfit_core.ui.theme

import androidx.compose.ui.graphics.Color

// ---- Brand ---- (verify exact hex against Figma if available)
val PulseGreen = Color(0xFF1FD8A6)       // primary brand green — logo, buttons, pose overlay dots
val PulseGreenDark = Color(0xFF139C79)   // pressed / container state
val PulseGreenLight = Color(0xFF6FF0C9)  // highlights, secondary accents

// ---- Surfaces (dark theme — the app's primary/only shipped theme) ----
val BackgroundDark = Color(0xFF0B1220)       // app background
val SurfaceDark = Color(0xFF141C2B)          // cards (dashboard stat cards, list containers)
val SurfaceVariantDark = Color(0xFF1C2636)   // nested rows, list items inside a card
val SurfaceElevatedDark = Color(0xFF232E42)  // sheets, dialogs, elevated chips/badges

// ---- Text (dark theme) ----
val TextPrimaryDark = Color(0xFFF5F7FA)
val TextSecondaryDark = Color(0xFF95A1B2)
val TextTertiaryDark = Color(0xFF5D6B80)

// ---- Borders / dividers ----
val BorderDark = Color(0xFF26303F)

// ---- Status ----
val StatusWarning = Color(0xFFF5A623)
val StatusError = Color(0xFFEF4444)          // "End workout", hang-up button
val StatusErrorContainer = Color(0xFF3A1518)

// ---- On-colors ----
val OnPulseGreen = Color(0xFF04140D)         // dark text on bright green buttons/badges
val OnError = Color(0xFFFFFFFF)

// ---- Chart gradient stops (recovery score bar, trend lines) ----
val ChartGreen = Color(0xFF22C55E)
val ChartYellow = Color(0xFFF5D423)
val ChartOrange = Color(0xFFF5A623)
val ChartRed = Color(0xFFEF4444)