package com.example.features.login.viewmodel

import com.example.syncfit_core.viewmodel.UiState

data class LoginUiState(
    val savedUsername: String? = null,
    val enteredUsername: String = "",
    val isCheckingLogin: Boolean = true,
) : UiState {
    val isUserLoggedIn: Boolean
        get() = !savedUsername.isNullOrBlank()
}
