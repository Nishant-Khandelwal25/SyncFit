package com.example.syncfit_core.healthconnect.model

data class TodayHealthSummary(
    val steps: Long,
    val totalCaloriesKcal: Double,
    val heartRateSummary: HeartRateSummary,
    val sleepSummary: SleepSummary?,
)
