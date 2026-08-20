package com.example.features.aiformcheck.data.exercise

data class SquatConfig(
    // Angle when user is standing straight
    val standingAngle: Double = 170.0,
    // Angle when user is descending into squat
    val descendingAngle: Double = 150.0,
    // Angle when user is at the bottom of squat
    val bottomAngle: Double = 100.0,
    // Angle when user is ascending from squat
    val ascendingAngle: Double = 110.0,
    // Minimum visibility threshold for landmarks to be considered valid
    val visibilityThreshold: Float = 0.5f,
)
