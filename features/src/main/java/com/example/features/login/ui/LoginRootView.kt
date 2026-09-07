package com.example.features.login.ui

import androidx.compose.runtime.Composable
import com.example.features.login.viewmodel.LoginViewModel

@Composable
fun LoginRootView(viewModel: LoginViewModel) {
    LoginView(viewModel::onAction)
}
