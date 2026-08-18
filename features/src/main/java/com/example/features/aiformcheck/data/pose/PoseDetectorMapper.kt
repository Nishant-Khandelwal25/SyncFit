package com.example.features.aiformcheck.data.pose

import com.example.features.aiformcheck.domain.model.Pose
import com.example.features.aiformcheck.domain.model.PoseLandmark
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarkerResult

fun PoseLandmarkerResult.toDomainModel(): Pose {

    val landmark = landmarks().firstOrNull()?.map { landmark ->
        PoseLandmark(
            x = landmark.x(),
            y = landmark.y(),
            z = landmark.z(),
            visibility = landmark.visibility().orElse(0f),
            presence = landmark.presence().orElse(0F),
        )
    } ?: emptyList()

    val worldLandmark = worldLandmarks().firstOrNull()?.map { landmark ->
        PoseLandmark(
            x = landmark.x(),
            y = landmark.y(),
            z = landmark.z(),
            visibility = landmark.visibility().orElse(0f),
            presence = landmark.presence().orElse(0F),
        )
    } ?: emptyList()

    return Pose(
        landmarks = landmark,
        worldLandmarks = worldLandmark,
    )
}
