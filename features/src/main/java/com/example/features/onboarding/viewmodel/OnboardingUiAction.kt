package com.example.features.onboarding.viewmodel

import com.example.syncfit_core.viewmodel.UiAction

sealed interface OnboardingUiAction : UiAction {
    data object GetStarted : OnboardingUiAction
}
