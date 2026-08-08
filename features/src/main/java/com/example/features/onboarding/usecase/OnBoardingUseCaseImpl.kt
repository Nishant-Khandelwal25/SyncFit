package com.example.features.onboarding.usecase

import com.example.syncfit_core.localRepository.SyncFitStorageLocalRepositoryImpl
import javax.inject.Inject

class OnBoardingUseCaseImpl @Inject constructor(
    private val localRepositoryImpl: SyncFitStorageLocalRepositoryImpl,
) : OnBoardingUseCase {
    override suspend fun setUserHasOnboarded(hasOnboarded: Boolean) {
        localRepositoryImpl.setUserHasOnboarded(hasOnboarded)
    }

    override suspend fun getUserHasOnboarded(): Boolean {
        return localRepositoryImpl.getUserHasOnboarded()
    }
}
