package com.example.features.onboarding.ui

import androidx.compose.runtime.Composable
import com.example.features.onboarding.viewmodel.OnboardingViewModel

@Composable
fun OnboardingRootView(
    viewModel: OnboardingViewModel,
) {
    OnboardingScreen(viewModel::handleAction)
}
