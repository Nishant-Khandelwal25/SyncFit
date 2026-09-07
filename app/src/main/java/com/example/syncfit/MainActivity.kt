package com.example.syncfit

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.features.login.viewmodel.LoginViewModel
import com.example.features.onboarding.viewmodel.OnboardingViewModel
import com.example.syncfit_core.ui.theme.SyncFitTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val onboardingViewModel: OnboardingViewModel by viewModels()
    private val loginViewModel: LoginViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition {
            onboardingViewModel.state.value.isCheckingOnboarding || loginViewModel.state.value.isCheckingLogin
        }

        enableEdgeToEdge()
        setContent {
            val onboardViewState by onboardingViewModel.state.collectAsStateWithLifecycle()
            val loginViewState by loginViewModel.state.collectAsStateWithLifecycle()
            SyncFitTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .systemBarsPadding(),
                    contentAlignment = Alignment.Center,
                ) {
                    AppNavigation(
                        onboardViewState.hasOnboarded,
                        loginViewState.isUserLoggedIn,
                        onboardingViewModel,
                        loginViewModel,
                    )
                }
            }
        }
    }
}
