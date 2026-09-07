package com.example.features.login.usecase

import kotlinx.coroutines.flow.Flow

interface LoginUseCase {
    val username: Flow<String?>
    suspend fun loginUser(username: String)

    suspend fun logoutUser()
}
