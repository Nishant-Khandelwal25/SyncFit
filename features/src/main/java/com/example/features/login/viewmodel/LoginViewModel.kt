package com.example.features.login.viewmodel

import com.example.features.login.usecase.LoginUseCase
import com.example.syncfit_core.viewmodel.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : BaseViewModel<LoginUiState, LoginUiAction, LoginUiEvent>(LoginUiState()) {

    init {
        checkLoginStatus()
    }

    private fun checkLoginStatus() {
        launch(onError = { setState { copy(isCheckingLogin = false) } }) {
            loginUseCase.username.collectLatest {
                setState { copy(savedUsername = it, isCheckingLogin = false) }
            }
        }
    }

    override fun handleAction(action: LoginUiAction) {
        when (action) {
            is LoginUiAction.Login -> {
                loginUser(action.username)
            }
        }
    }

    private fun loginUser(username: String) {
        launch {
            loginUseCase.loginUser(username)
        }
    }
}
