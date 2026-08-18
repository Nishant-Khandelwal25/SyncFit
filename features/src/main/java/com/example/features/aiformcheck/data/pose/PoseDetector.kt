package com.example.features.aiformcheck.data.pose

import com.example.features.aiformcheck.domain.model.Pose
import com.google.mediapipe.framework.image.MPImage
import kotlinx.coroutines.flow.Flow

interface PoseDetector {
    val results: Flow<Pose>

    fun detect(image: MPImage, timestamp: Long)

    fun stop()
}
