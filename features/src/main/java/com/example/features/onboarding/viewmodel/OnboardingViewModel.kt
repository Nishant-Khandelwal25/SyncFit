package com.example.features.onboarding.viewmodel

import android.util.Log
import com.example.features.onboarding.usecase.OnBoardingUseCase
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(private val onBoardingUseCase: OnBoardingUseCase) :
    BaseViewModel<OnboardingUiState, OnboardingUiAction, OnboardingUiEvent>(OnboardingUiState()) {

    fun onCreate() {
        launch {
            val isOnboarded = onBoardingUseCase.getUserHasOnboarded()
            setState { copy(hasOnboarded = isOnboarded) }
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
            Log.d("TAG", "updateOnboardingStatus: ${onBoardingUseCase.getUserHasOnboarded()}")
        }
    }
}
