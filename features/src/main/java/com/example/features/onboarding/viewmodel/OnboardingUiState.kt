package com.example.features.onboarding.viewmodel

import com.example.syncfit_core.viewmodel.UiState

data class OnboardingUiState(
    val hasOnboarded: Boolean = false,
    val isCheckingOnboarding: Boolean = true,
) : UiState
