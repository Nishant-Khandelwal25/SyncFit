package com.example.features.login.viewmodel

import com.example.syncfit_core.viewmodel.UiAction

sealed interface LoginUiAction : UiAction {
    data class Login(val username: String) : LoginUiAction
}
