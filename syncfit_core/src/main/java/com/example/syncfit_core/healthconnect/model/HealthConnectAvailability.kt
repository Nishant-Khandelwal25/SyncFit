package com.example.syncfit_core.healthconnect.model

sealed interface HealthConnectAvailability {
    data object Available : HealthConnectAvailability
    data object NeedsProviderUpdate : HealthConnectAvailability
    data object Unavailable : HealthConnectAvailability
}
