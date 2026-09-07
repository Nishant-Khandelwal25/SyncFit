package com.example.features.login.usecase

import com.example.syncfit_core.localRepository.SyncFitStorageLocalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LoginUseCaseImpl @Inject constructor(
    private val localRepositoryImpl: SyncFitStorageLocalRepository,
) : LoginUseCase {
    override val username: Flow<String?>
        get() = localRepositoryImpl.username

    override suspend fun loginUser(username: String) = localRepositoryImpl.loginUser(username)

    override suspend fun logoutUser() = localRepositoryImpl.logoutUser()

}
