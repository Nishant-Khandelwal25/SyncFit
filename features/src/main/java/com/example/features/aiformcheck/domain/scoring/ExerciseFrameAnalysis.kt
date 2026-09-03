package com.example.features.aiformcheck.domain.scoring

data class ExerciseFrameAnalysis(
    val timestamp: Long,
    val isValidPose: Boolean,
    val repEvent: RepEvent = RepEvent.NONE,
    val metrics: Map<FormMetricKey, Double> = emptyMap(),
)
