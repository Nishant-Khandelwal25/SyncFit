package com.example.syncfit_core.localRepository

interface SyncFitStorageLocalRepository {
    suspend fun setUserHasOnboarded(hasOnboarded: Boolean)

    suspend fun getUserHasOnboarded(): Boolean

    suspend fun setHasRequestedCameraPermission(hasRequested: Boolean)
    suspend fun getHasRequestedCameraPermission(): Boolean
}
