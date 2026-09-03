package com.example.features.aiformcheck.domain.exercise

import com.example.features.aiformcheck.domain.scoring.FormMetricKey

object SquatFormMetrics {
    val averageKneeAngle = FormMetricKey("squat_average_knee_angle")
    val leftKneeAngle = FormMetricKey("squat_left_knee_angle")
    val rightKneeAngle = FormMetricKey("squat_right_knee_angle")
    val legSymmetry = FormMetricKey("squat_leg_symmetry")
    val lockoutAngle = FormMetricKey("squat_lockout_angle")
}
