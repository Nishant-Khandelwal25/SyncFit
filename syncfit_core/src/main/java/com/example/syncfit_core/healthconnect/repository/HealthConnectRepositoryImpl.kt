package com.example.syncfit_core.healthconnect.repository

import android.content.Context
import androidx.health.connect.client.HealthConnectClient
import androidx.health.connect.client.permission.HealthPermission
import androidx.health.connect.client.records.HeartRateRecord
import androidx.health.connect.client.records.SleepSessionRecord
import androidx.health.connect.client.records.StepsRecord
import androidx.health.connect.client.records.TotalCaloriesBurnedRecord
import androidx.health.connect.client.request.AggregateRequest
import androidx.health.connect.client.time.TimeRangeFilter
import com.example.syncfit_core.healthconnect.model.HealthConnectAvailability
import com.example.syncfit_core.healthconnect.model.HeartRateSummary
import com.example.syncfit_core.healthconnect.model.TodayHealthSummary
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.Instant
import androidx.health.connect.client.request.ReadRecordsRequest
import com.example.syncfit_core.healthconnect.model.SleepSummary
import java.time.Duration
import java.time.LocalDate
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HealthConnectRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : HealthConnectRepository {

    private val healthConnectClient: HealthConnectClient
        get() = HealthConnectClient.getOrCreate(context)

    override val requiredPermissions = setOf(
        HealthPermission.getReadPermission(StepsRecord::class),
        HealthPermission.getReadPermission(SleepSessionRecord::class),
        HealthPermission.getReadPermission(HeartRateRecord::class),
        HealthPermission.getReadPermission(TotalCaloriesBurnedRecord::class),
    )

    override suspend fun getAvailability(): HealthConnectAvailability {
        return when (HealthConnectClient.getSdkStatus(context)) {
            HealthConnectClient.SDK_AVAILABLE -> HealthConnectAvailability.Available

            HealthConnectClient.SDK_UNAVAILABLE_PROVIDER_UPDATE_REQUIRED -> HealthConnectAvailability.NeedsProviderUpdate

            else -> HealthConnectAvailability.Unavailable
        }
    }

    override suspend fun getGrantedPermissions(): Set<String> {
        return healthConnectClient.permissionController.getGrantedPermissions()
    }

    override suspend fun readTodaySummary(): TodayHealthSummary {
        val now = Instant.now()
        val todayStart = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()
        val steps = readSteps(todayStart, now)
        val totalBurnedCalories = readTotalBurnedCalories(todayStart, now)
        val heartRate = readHeardRate(todayStart, now)
        val sleepSummary = readLatestSleep(todayStart.minus(Duration.ofDays(1)), now)
        return TodayHealthSummary(steps, totalBurnedCalories, heartRate, sleepSummary)
    }

    private suspend fun readSteps(startTime: Instant, endTime: Instant): Long {
        val result = healthConnectClient.aggregate(
            AggregateRequest(
                metrics = setOf(StepsRecord.COUNT_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ),
        )

        return result[StepsRecord.COUNT_TOTAL] ?: 0L
    }

    private suspend fun readTotalBurnedCalories(startTime: Instant, endTime: Instant): Double {
        val result = healthConnectClient.aggregate(
            AggregateRequest(
                metrics = setOf(TotalCaloriesBurnedRecord.ENERGY_TOTAL),
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ),
        )
        return result[TotalCaloriesBurnedRecord.ENERGY_TOTAL]?.inKilocalories ?: 0.0
    }

    private suspend fun readHeardRate(startTime: Instant, endTime: Instant): HeartRateSummary {
        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                recordType = HeartRateRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ),
        )

        val samples = response.records.flatMap { it.samples }.sortedBy { it.time }

        return HeartRateSummary(
            latestBpm = samples.lastOrNull()?.beatsPerMinute,
            averageBpm = samples.map { it.beatsPerMinute.toDouble() }.average().takeIf { !it.isNaN() },
        )
    }

    private suspend fun readLatestSleep(startTime: Instant, endTime: Instant): SleepSummary? {
        val response = healthConnectClient.readRecords(
            ReadRecordsRequest(
                recordType = SleepSessionRecord::class,
                timeRangeFilter = TimeRangeFilter.between(startTime, endTime),
            ),
        )

        val sleepSessions = response.records.maxByOrNull { it.endTime } ?: return null

        return SleepSummary(
            startTime = sleepSessions.startTime,
            endTime = sleepSessions.endTime,
            durationMinutes = Duration.between(sleepSessions.startTime, sleepSessions.endTime).toMinutes(),
            title = sleepSessions.title,
        )
    }
}
