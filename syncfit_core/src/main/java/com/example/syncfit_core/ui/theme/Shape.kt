package com.example.syncfit_core.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val SyncFitShapes = Shapes(
    extraSmall = RoundedCornerShape(6.dp),   // chips, small badges (e.g. "8 reps" pill)
    small = RoundedCornerShape(10.dp),       // buttons, tags, pills
    medium = RoundedCornerShape(16.dp),      // cards, list rows
    large = RoundedCornerShape(24.dp),       // bottom sheets, dialogs, hero cards
    extraLarge = RoundedCornerShape(32.dp)   // full-bleed camera overlay panel
)