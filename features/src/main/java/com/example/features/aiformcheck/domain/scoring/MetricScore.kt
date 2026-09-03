package com.example.features.aiformcheck.domain.scoring

data class MetricScore(
    val metricKey: FormMetricKey,
    val measuredValue: Double,
    val score: Int,
)
