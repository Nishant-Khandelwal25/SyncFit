package com.example.features.aiformcheck.data.pose

import android.content.Context
import com.example.features.aiformcheck.domain.model.Pose
import com.google.mediapipe.framework.image.MPImage
import com.google.mediapipe.tasks.core.BaseOptions
import com.google.mediapipe.tasks.vision.core.RunningMode
import com.google.mediapipe.tasks.vision.poselandmarker.PoseLandmarker
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class MediaPipePoseDetector @Inject constructor(private val context: Context) : PoseDetector {
    private val _results =
        MutableSharedFlow<Pose>(replay = 0, extraBufferCapacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    override val results: Flow<Pose> = _results

    private var poseLandmarker: PoseLandmarker? = null

    init {
        setup()
    }

    private fun setup() {
        val baseOptions = BaseOptions.builder().setModelAssetPath("models/pose_landmarker_full.task").build()

        val options = PoseLandmarker.PoseLandmarkerOptions.builder()
            .setBaseOptions(baseOptions)
            .setRunningMode(RunningMode.LIVE_STREAM)
            .setNumPoses(1)
            .setMinPoseDetectionConfidence(0.5f)
            .setMinPosePresenceConfidence(0.5f)
            .setMinTrackingConfidence(0.5f)
            .setResultListener { result, _ ->
                val pose = result.toDomainModel()

                _results.tryEmit(pose)
            }
            .setErrorListener { error -> error.printStackTrace() }
            .build()

        poseLandmarker = PoseLandmarker.createFromOptions(context, options)
    }

    override fun detect(image: MPImage, timestamp: Long) {
        poseLandmarker?.detectAsync(image, timestamp)
    }

    override fun stop() {
        poseLandmarker?.close()
        poseLandmarker = null
    }
}
