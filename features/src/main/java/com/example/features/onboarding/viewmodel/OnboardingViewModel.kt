package com.example.features.onboarding.viewmodel

import com.example.features.onboarding.usecase.OnBoardingUseCase
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val onBoardingUseCase: OnBoardingUseCase) :
    BaseViewModel<OnboardingUiState, OnboardingUiAction, OnboardingUiEvent>(OnboardingUiState()) {

    init {
        checkOnboardingStatus()
    }

    private fun checkOnboardingStatus() {
        launch(onError = { setState { copy(hasOnboarded = false, isCheckingOnboarding = false) } }) {
            val hasOnboarded = onBoardingUseCase.getUserHasOnboarded()
            setState { copy(hasOnboarded = hasOnboarded, isCheckingOnboarding = false) }
        }
    }

    override fun handleAction(action: OnboardingUiAction) {
        when (action) {
            is OnboardingUiAction.GetStarted -> {
                updateOnboardingStatus()
            }
        }
    }

    private fun updateOnboardingStatus() {
        launch {
            setState { copy(hasOnboarded = true) }
            onBoardingUseCase.setUserHasOnboarded(true)
        }
    }
}
