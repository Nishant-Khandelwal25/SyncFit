package com.example.features.home.usecase

interface HomeScreenUseCase {
    suspend fun setHasRequestedCameraPermission(hasRequested: Boolean)
    suspend fun getHasRequestedCameraPermission(): Boolean
}
