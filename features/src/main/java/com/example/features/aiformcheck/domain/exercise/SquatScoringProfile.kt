package com.example.features.aiformcheck.domain.exercise

import com.example.features.aiformcheck.domain.scoring.CommonFormMetrics
import com.example.features.aiformcheck.domain.scoring.ExerciseScoringProfile
import com.example.features.aiformcheck.domain.scoring.FormMetricRule
import com.example.features.aiformcheck.domain.scoring.MetricAggregation
import com.example.features.aiformcheck.domain.scoring.ScoreCurves

object SquatScoringProfile {
    val profile = ExerciseScoringProfile(
        minimumValidFrameRatio = 0.80f,
        metricRules = listOf(
            FormMetricRule(
                metricKey = SquatFormMetrics.averageKneeAngle,
                weight = 0.40,
                aggregation = MetricAggregation.MINIMUM,
                scoreCurve = ScoreCurves.lowerIsBetter(90.0, 120.0),
                feedbackThreshold = 70,
                feedback = "Try reaching a little more depth",
            ),
            FormMetricRule(
                metricKey = SquatFormMetrics.legSymmetry,
                weight = 0.25,
                aggregation = MetricAggregation.AVERAGE,
                scoreCurve = ScoreCurves.lowerIsBetter(5.0, 25.0),
                feedbackThreshold = 70,
                feedback = "Keep both legs moving evenly",
            ),
            FormMetricRule(
                metricKey = SquatFormMetrics.lockoutAngle,
                weight = 0.20,
                aggregation = MetricAggregation.LAST,
                scoreCurve = ScoreCurves.higherIsBetter(170.0, 150.0),
                feedbackThreshold = 70,
                feedback = "Finish the rep by standing tall",
            ),
            FormMetricRule(
                metricKey = CommonFormMetrics.repDuration,
                weight = 0.15,
                aggregation = MetricAggregation.LAST,
                scoreCurve = ScoreCurves.idealRange(
                    hardMinimum = 600.0,
                    idealMinimum = 1_500.00,
                    idealMaximum = 5_000.0,
                    hardMaximum = 8_000.0,
                ),
                feedbackThreshold = 70,
                feedback = "Use a more controlled movement",
            ),
        ),
    )
}
