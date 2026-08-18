package com.example.features.aiformcheck.ui

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.drawscope.DrawScope
import com.example.features.aiformcheck.data.camera.CameraFrameInfo
import com.example.features.aiformcheck.data.pose.POSE_CONNECTIONS
import com.example.features.aiformcheck.domain.model.Pose
import com.example.syncfit_core.ui.theme.ChartGreen
import kotlin.math.max

@Composable
fun PoseOverlay(
    pose: Pose?,
    frameInfo: CameraFrameInfo?,
    modifier: Modifier = Modifier,
) {
    Canvas(modifier = modifier) {
        pose ?: return@Canvas

        drawPose(pose, size.width, size.height, frameInfo)
    }
}

private fun DrawScope.drawPose(pose: Pose, canvasWidth: Float, canvasHeight: Float, frameInfo: CameraFrameInfo?) {
    val imageWidth = frameInfo?.width?.toFloat() ?: return
    val imageHeight = frameInfo.height.toFloat()
    val rotationDegrees = frameInfo.rotationDegrees

    val isQuarterTurn = rotationDegrees == 90 || rotationDegrees == 270
    val rotatedImageWidth = if (isQuarterTurn) imageHeight else imageWidth
    val rotatedImageHeight = if (isQuarterTurn) imageWidth else imageHeight

    val scale = max(canvasWidth / rotatedImageWidth, canvasHeight / rotatedImageHeight)

    val scaledWidth = rotatedImageWidth * scale
    val scaledHeight = rotatedImageHeight * scale

    val offsetX = (canvasWidth - scaledWidth) / 2f
    val offsetY = (canvasHeight - scaledHeight) / 2f

    val landmarks = pose.landmarks
    for (connection in POSE_CONNECTIONS) {
        val start = landmarks.getOrNull(connection.start) ?: continue
        val end = landmarks.getOrNull(connection.end) ?: continue

        if (start.visibility < 0.5f || end.visibility < 0.5f) {
            continue
        }

        val startPoint = transformLandmark(
            landmarkX = start.x,
            landmarkY = start.y,
            imageWidth = imageWidth,
            imageHeight = imageHeight,
            scale = scale,
            offsetX = offsetX,
            offsetY = offsetY,
            rotationDegrees = frameInfo.rotationDegrees,
        )

        val endPoint = transformLandmark(
            landmarkX = end.x,
            landmarkY = end.y,
            imageWidth = imageWidth,
            imageHeight = imageHeight,
            scale = scale,
            offsetX = offsetX,
            offsetY = offsetY,
            rotationDegrees = frameInfo.rotationDegrees,
        )

        drawLine(color = ChartGreen, start = startPoint, end = endPoint, strokeWidth = 8f)
    }

    landmarks.forEach { landmark ->
        if (landmark.visibility < 0.5f) {
            return@forEach
        }

        val point = transformLandmark(
            landmarkX = landmark.x,
            landmarkY = landmark.y,
            imageWidth = imageWidth,
            imageHeight = imageHeight,
            scale = scale,
            offsetX = offsetX,
            offsetY = offsetY,
            rotationDegrees = frameInfo.rotationDegrees,
        )

        drawCircle(color = ChartGreen, radius = 10f, center = point)
    }
}

private fun transformLandmark(
    landmarkX: Float,
    landmarkY: Float,
    imageWidth: Float,
    imageHeight: Float,
    scale: Float,
    offsetX: Float,
    offsetY: Float,
    rotationDegrees: Int,
): Offset {
    var x = landmarkX * imageWidth
    var y = landmarkY * imageHeight

    when (rotationDegrees) {
        90 -> {
            val rotatedX = imageHeight - y
            val rotatedY = x
            x = rotatedX
            y = rotatedY
        }

        180 -> {
            x = imageWidth - x
            y = imageHeight - y
        }

        270 -> {
            val rotatedX = y
            val rotatedY = imageWidth - x
            x = rotatedX
            y = rotatedY
        }
    }

    return Offset(x = x * scale + offsetX, y = y * scale + offsetY)
}
