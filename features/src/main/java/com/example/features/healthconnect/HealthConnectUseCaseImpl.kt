package com.example.features.healthconnect

import com.example.syncfit_core.healthconnect.model.HealthConnectAvailability
import com.example.syncfit_core.healthconnect.model.TodayHealthSummary
import com.example.syncfit_core.healthconnect.repository.HealthConnectRepositoryImpl
import javax.inject.Inject

class HealthConnectUseCaseImpl @Inject constructor(
    private val repository: HealthConnectRepositoryImpl,
) : HealthConnectUseCase {
    override val requiredPermissions: Set<String>
        get() = repository.requiredPermissions

    override suspend fun getAvailability(): HealthConnectAvailability {
        return repository.getAvailability()
    }

    override suspend fun hasRequiredPermissions(): Boolean {
        val grantedPermissions = repository.getGrantedPermissions()
        return grantedPermissions.containsAll(requiredPermissions)
    }

    override suspend fun readTodaySummary(): TodayHealthSummary {
        return repository.readTodaySummary()
    }
}
