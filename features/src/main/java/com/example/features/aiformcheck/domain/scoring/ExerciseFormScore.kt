package com.example.features.aiformcheck.domain.scoring

data class ExerciseFormScore(
    val overallScore: Int?,
    val metricScores: List<MetricScore>,
    val confidence: Float,
    val feedback: List<String>,
    val durationMillis: Long,
)
