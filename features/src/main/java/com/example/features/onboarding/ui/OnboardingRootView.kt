package com.example.features.onboarding.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import com.example.features.onboarding.viewmodel.OnboardingViewModel

@Composable
fun OnboardingRootView(
    viewModel: OnboardingViewModel,
) {
    val isLaunched = rememberSaveable { mutableStateOf(false) }
    if (!isLaunched.value) {
        isLaunched.value = true
        viewModel.onCreate()
    }
    OnboardingScreen(viewModel::handleAction)
}
