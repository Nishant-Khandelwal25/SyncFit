package com.example.features.aiformcheck.data.camera

import android.os.SystemClock
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.features.aiformcheck.data.pose.PoseDetector
import com.google.mediapipe.framework.image.MediaImageBuilder
import javax.inject.Inject

class CameraFrameAnalyzer @Inject constructor(
    private val poseDetector: PoseDetector,
    private val onFrameInfo: (CameraFrameInfo) -> Unit,
) : ImageAnalysis.Analyzer {


    @OptIn(ExperimentalGetImage::class)
    override fun analyze(imageProxy: ImageProxy) {
        imageProxy.use { imageProxy ->
            val frameInfo = CameraFrameInfo(
                width = imageProxy.width,
                height = imageProxy.height,
                rotationDegrees = imageProxy.imageInfo.rotationDegrees,
            )

            onFrameInfo(frameInfo)
            val mediaImage = imageProxy.image ?: return
            val mpImage = MediaImageBuilder(mediaImage).build()
            poseDetector.detect(mpImage, SystemClock.uptimeMillis())
        }
    }
}
