package com.example.syncfit_core.healthconnect.model

import java.time.Instant

data class SleepSummary(
    val startTime: Instant?,
    var endTime: Instant?,
    val durationMinutes: Long,
    val title: String?,
)
