package com.example.features.aiformcheck.domain.exercise

import com.example.features.aiformcheck.constants.LEFT_ANKLE
import com.example.features.aiformcheck.constants.LEFT_HIP
import com.example.features.aiformcheck.constants.LEFT_KNEE
import com.example.features.aiformcheck.constants.RIGHT_ANKLE
import com.example.features.aiformcheck.constants.RIGHT_HIP
import com.example.features.aiformcheck.constants.RIGHT_KNEE
import com.example.features.aiformcheck.data.enums.SquatPhase
import com.example.features.aiformcheck.data.exercise.SquatConfig
import com.example.features.aiformcheck.data.exercise.SquatResult
import com.example.features.aiformcheck.domain.model.Pose
import com.example.features.aiformcheck.util.CalculateAngleUtil
import javax.inject.Inject

class SquatAnalyzer @Inject constructor(private val config: SquatConfig) {
    private var phase = SquatPhase.STANDING

    private var reps = 0

    fun process(pose: Pose): SquatResult {
        val landmarks = pose.landmarks

        val leftHip = landmarks.getOrNull(LEFT_HIP)
        val leftKnee = landmarks.getOrNull(LEFT_KNEE)
        val leftAnkle = landmarks.getOrNull(LEFT_ANKLE)

        val rightHip = landmarks.getOrNull(RIGHT_HIP)
        val rightKnee = landmarks.getOrNull(RIGHT_KNEE)
        val rightAnkle = landmarks.getOrNull(RIGHT_ANKLE)

        if (
            leftHip == null ||
            leftKnee == null ||
            leftAnkle == null ||
            rightHip == null ||
            rightKnee == null ||
            rightAnkle == null
        ) {
            return invalidResult()
        }

        val isReliable =
            CalculateAngleUtil.areLandmarksReliable(
                leftHip,
                leftKnee,
                leftAnkle,
                rightHip,
                rightKnee,
                rightAnkle,
                threshold = config.visibilityThreshold,
            )

        if (!isReliable) {
            return invalidResult()
        }

        val leftKneeAngle = CalculateAngleUtil.calculate(leftHip, leftKnee, leftAnkle)
        val rightKneeAngle = CalculateAngleUtil.calculate(rightHip, rightKnee, rightAnkle)

        val kneeAngle = CalculateAngleUtil.calculateAverageAngle(leftKneeAngle, rightKneeAngle)

        updatePhase(kneeAngle)

        return SquatResult(
            reps = reps,
            phase = phase,
            kneeAngle = kneeAngle,
            leftKneeAngle = leftKneeAngle,
            rightKneeAngle = rightKneeAngle,
            isValidPose = true,
        )
    }

    private fun updatePhase(kneeAngle: Double) {
        when (phase) {
            // User is standing, wait for knee angle to decrease
            SquatPhase.STANDING -> {
                if (kneeAngle < config.descendingAngle) {
                    phase = SquatPhase.DESCENDING
                }
            }

            // User is descending, wait for knee angle to reach bottom
            SquatPhase.DESCENDING -> {
                if (kneeAngle <= config.bottomAngle) {
                    phase = SquatPhase.BOTTOM
                }
            }

            // User is at the bottom, wait for knee angle to increase
            SquatPhase.BOTTOM -> {
                if (kneeAngle > config.ascendingAngle) {
                    phase = SquatPhase.ASCENDING
                }
            }

            // User is ascending, wait for knee angle to reach standing
            SquatPhase.ASCENDING -> {
                if (kneeAngle >= config.standingAngle) {
                    phase = SquatPhase.STANDING
                    reps++
                }
            }
        }
    }

    private fun invalidResult(): SquatResult {
        return SquatResult(
            reps = reps,
            phase = phase,
            kneeAngle = null,
            leftKneeAngle = null,
            rightKneeAngle = null,
            isValidPose = false,
        )
    }

    fun reset() {
        phase = SquatPhase.STANDING
        reps = 0
    }
}
