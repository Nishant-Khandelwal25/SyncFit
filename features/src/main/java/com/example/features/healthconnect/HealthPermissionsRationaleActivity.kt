package com.example.features.healthconnect

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import com.example.syncfit_core.ui.theme.SyncFitTheme

class HealthPermissionsRationaleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SyncFitTheme {
                HealthPermissionsRationaleScreen(
                    onContinue = {
                        finish()
                    },
                )
            }
        }
    }
}
