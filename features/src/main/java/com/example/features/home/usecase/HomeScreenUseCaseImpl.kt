package com.example.features.home.usecase

import com.example.syncfit_core.localRepository.SyncFitStorageLocalRepository
import javax.inject.Inject

class HomeScreenUseCaseImpl @Inject constructor(
    private val repository: SyncFitStorageLocalRepository,
) : HomeScreenUseCase {
    override suspend fun setHasRequestedCameraPermission(hasRequested: Boolean) {
        repository.setHasRequestedCameraPermission(hasRequested)
    }

    override suspend fun getHasRequestedCameraPermission(): Boolean {
        return repository.getHasRequestedCameraPermission()
    }
}
