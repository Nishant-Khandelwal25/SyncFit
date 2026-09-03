package com.example.features.aiformcheck.domain.scoring

data class FormMetricRule(
    val metricKey: FormMetricKey,
    val weight: Double,
    val aggregation: MetricAggregation,
    val scoreCurve: ScoreCurve,
    val feedbackThreshold: Int = 70,
    val feedback: String,
    val required: Boolean = true,
)
