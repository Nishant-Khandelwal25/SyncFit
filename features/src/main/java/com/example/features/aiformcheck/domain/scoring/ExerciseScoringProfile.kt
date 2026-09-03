package com.example.features.aiformcheck.domain.scoring

data class ExerciseScoringProfile(
    val metricRules: List<FormMetricRule>,
    val minimumValidFrameRatio: Float = 0.80f,
)
