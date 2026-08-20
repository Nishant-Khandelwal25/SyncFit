package com.example.features.aiformcheck.data.exercise

import com.example.features.aiformcheck.data.enums.SquatPhase

data class SquatResult(
    val reps: Int,
    val phase: SquatPhase,
    val kneeAngle: Double?,
    val leftKneeAngle: Double?,
    val rightKneeAngle: Double?,
    val isValidPose: Boolean,
)
