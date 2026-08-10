package com.example.features.home.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.features.home.viewmodel.HomeScreenViewModel

@Composable
fun HomeScreenRootView(viewModel: HomeScreenViewModel) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    viewModel.onCreate()
    HomeScreenContent(state)
}
