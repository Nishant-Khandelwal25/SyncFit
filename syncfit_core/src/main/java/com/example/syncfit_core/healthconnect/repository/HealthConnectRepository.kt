package com.example.syncfit_core.healthconnect.repository

import com.example.syncfit_core.healthconnect.model.HealthConnectAvailability
import com.example.syncfit_core.healthconnect.model.TodayHealthSummary

interface HealthConnectRepository {
    val requiredPermissions: Set<String>
    suspend fun getAvailability(): HealthConnectAvailability

    suspend fun getGrantedPermissions(): Set<String>

    suspend fun readTodaySummary(): TodayHealthSummary
}
