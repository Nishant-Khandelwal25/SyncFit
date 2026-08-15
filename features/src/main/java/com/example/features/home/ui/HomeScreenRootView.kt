package com.example.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.features.home.viewmodel.HomeScreenUiAction
import com.example.features.home.viewmodel.HomeScreenUiEvent
import com.example.features.home.viewmodel.HomeScreenUiState
import com.example.features.home.viewmodel.HomeScreenViewModel
import com.example.syncfit_core.navigation.Navigator
import com.example.syncfit_core.navigation.Routes
import com.example.syncfit_core.viewmodel.ObserveAsEvents

@Composable
fun HomeScreenRootView(viewModel: HomeScreenViewModel, navigator: Navigator) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    viewModel.onCreate()

    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is HomeScreenUiEvent.LaunchAIFormCheck -> {
                navigator.navigate(Routes.AIFormCheck)
            }
        }
    }

    HomeScreenContent(state, viewModel::onAction)
    HandlePermissionDialog(state, viewModel::onAction)
}

@Composable
fun HandlePermissionDialog(state: HomeScreenUiState, onAction: (HomeScreenUiAction) -> Unit) {
    if (!state.launchCameraPermissionDialog) return
    CameraPermissionRequestDialog(onAction)
}
