package com.example.features.aiformcheck.data.camera

import android.content.Context
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.lifecycle.awaitInstance
import androidx.lifecycle.LifecycleOwner
import com.example.features.aiformcheck.data.pose.PoseDetector
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.Executors
import javax.inject.Inject

class CameraController @Inject constructor(private val poseDetector: PoseDetector) {
    private val cameraExecutor = Executors.newSingleThreadExecutor()
    private var cameraProvider: ProcessCameraProvider? = null

    val preview: Preview = Preview.Builder().build()

    private val _frameInfo = MutableStateFlow<CameraFrameInfo?>(null)
    val frameInfo: StateFlow<CameraFrameInfo?> = _frameInfo.asStateFlow()

    val imageAnalysis = ImageAnalysis.Builder()
        .setOutputImageFormat(ImageAnalysis.OUTPUT_IMAGE_FORMAT_RGBA_8888)
        .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
        .build().apply {
            setAnalyzer(
                cameraExecutor,
                CameraFrameAnalyzer(poseDetector) {
                    if (_frameInfo.value != it) {
                        _frameInfo.value = it
                    }
                },
            )
        }

    suspend fun bind(context: Context, lifecycleOwner: LifecycleOwner) {
        val provider = ProcessCameraProvider.awaitInstance(context)
        cameraProvider = provider
        provider.bindToLifecycle(lifecycleOwner, CameraSelector.DEFAULT_BACK_CAMERA, preview, imageAnalysis)
    }

    fun unbind() {
        cameraProvider?.unbindAll()
        cameraProvider = null

    }

    fun close() {
        unbind()
        cameraExecutor.shutdown()

    }
}
