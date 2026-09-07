package com.example.syncfit_core.localRepository

import kotlinx.coroutines.flow.Flow

interface SyncFitStorageLocalRepository {
    suspend fun setUserHasOnboarded(hasOnboarded: Boolean)

    suspend fun getUserHasOnboarded(): Boolean

    suspend fun setHasRequestedCameraPermission(hasRequested: Boolean)
    suspend fun getHasRequestedCameraPermission(): Boolean

    suspend fun loginUser(username: String)
    val username: Flow<String?>
    suspend fun logoutUser()
}
