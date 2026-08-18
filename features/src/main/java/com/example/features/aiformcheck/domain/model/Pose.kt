package com.example.features.aiformcheck.domain.model

data class Pose(
    val landmarks: List<PoseLandmark>,
    val worldLandmarks: List<PoseLandmark>,
)
