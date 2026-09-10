package com.example.features.healthconnect

import com.example.syncfit_core.healthconnect.model.HealthConnectAvailability
import com.example.syncfit_core.healthconnect.model.TodayHealthSummary

interface HealthConnectUseCase {
    val requiredPermissions: Set<String>
    suspend fun getAvailability(): HealthConnectAvailability

    suspend fun hasRequiredPermissions(): Boolean

    suspend fun readTodaySummary(): TodayHealthSummary
}
