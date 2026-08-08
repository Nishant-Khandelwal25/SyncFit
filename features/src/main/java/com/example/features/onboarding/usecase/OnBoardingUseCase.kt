package com.example.features.onboarding.usecase

interface OnBoardingUseCase {
    suspend fun setUserHasOnboarded(hasOnboarded: Boolean)

    suspend fun getUserHasOnboarded(): Boolean
}
