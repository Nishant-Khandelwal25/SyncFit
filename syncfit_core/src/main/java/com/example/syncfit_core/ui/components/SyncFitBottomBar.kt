package com.example.syncfit_core.ui.components

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation3.runtime.NavKey
import com.example.syncfit_core.navigation.Routes
import com.example.syncfit_core.navigation.model.BottomTab
import com.example.syncfit_core.ui.theme.SurfaceDark

@Composable
fun SyncFitBottomBar(
    selectedRoute: NavKey,
    bottomTabs: List<BottomTab>,
    onTabSelected: (Routes) -> Unit,
) {
    NavigationBar(containerColor = SurfaceDark) {
        bottomTabs.forEach { tab ->
            val selected = selectedRoute == tab.route
            val label = stringResource(tab.labelResId)

            NavigationBarItem(
                selected = selected,
                onClick = {
                    if (!selected) {
                        onTabSelected(tab.route)
                    }
                },
                icon = {
                    Icon(
                        imageVector = if (selected) tab.selectedIcon else tab.unselectedIcon,
                        contentDescription = null,
                    )
                },
                label = { SyncFitText(text = label) },
                alwaysShowLabel = true,
            )
        }
    }
}
