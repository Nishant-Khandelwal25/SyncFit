package com.example.syncfit_core.navigation.model

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.syncfit_core.navigation.Routes

data class BottomTab(
    val route: Routes,
    val labelResId: Int,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
)
