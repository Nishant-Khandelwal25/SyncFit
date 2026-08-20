package com.example.features.aiformcheck.util

import com.example.features.aiformcheck.domain.model.PoseLandmark
import kotlin.math.abs
import kotlin.math.atan2

object CalculateAngleUtil {
    fun calculate(a: PoseLandmark, b: PoseLandmark, c: PoseLandmark): Double {
        val radians = atan2(c.y - b.y, c.x - b.x) - atan2(a.y - b.y, a.x - b.x)

        var degrees = abs(Math.toDegrees(radians.toDouble()))

        if (degrees > 180) {
            degrees = 360 - degrees
        }
        return degrees
    }

    fun calculateAverageAngle(first: Double, second: Double) = (first + second) / 2.0

    fun areLandmarksReliable(
        vararg landmarks: PoseLandmark,
        threshold: Float = 0.5f,
    ) = landmarks.all { it.visibility >= threshold }

}
