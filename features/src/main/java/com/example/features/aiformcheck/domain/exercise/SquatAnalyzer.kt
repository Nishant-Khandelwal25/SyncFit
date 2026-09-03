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
import com.example.features.aiformcheck.domain.scoring.ExerciseFormScore
import com.example.features.aiformcheck.domain.scoring.ExerciseFormScorer
import com.example.features.aiformcheck.domain.scoring.ExerciseFrameAnalysis
import com.example.features.aiformcheck.domain.scoring.ExerciseMotionAnalyser
import com.example.features.aiformcheck.domain.scoring.RepEvent
import com.example.features.aiformcheck.util.CalculateAngleUtil
import javax.inject.Inject
import kotlin.math.abs

class SquatAnalyzer @Inject constructor(private val config: SquatConfig) : ExerciseMotionAnalyser {
    private var phase = SquatPhase.STANDING

    private var reps = 0
    private val formScorer = ExerciseFormScorer(profile = SquatScoringProfile.profile)
    private var lastRepFormScore: ExerciseFormScore? = null
    private val completedRepScores = mutableListOf<Int>()

    fun process(pose: Pose, timeStampMillis: Long): SquatResult {
        val frameAnalysis = analyse(pose, timeStampMillis)
        val scoringUpdate = formScorer.processFrame(frameAnalysis)

        scoringUpdate.completedScore?.let { completedScore ->
            lastRepFormScore = completedScore
            completedScore.overallScore?.let { score ->
                completedRepScores += score
            }
        }

        return SquatResult(
            reps = reps,
            phase = phase,
            kneeAngle = frameAnalysis.metrics[SquatFormMetrics.averageKneeAngle],
            leftKneeAngle = frameAnalysis.metrics[SquatFormMetrics.leftKneeAngle],
            rightKneeAngle = frameAnalysis.metrics[SquatFormMetrics.rightKneeAngle],
            isValidPose = frameAnalysis.isValidPose,
            lastRepFormScore = lastRepFormScore,
            averageFormScore = calculateAverageFormScore(),
        )
    }


    override fun analyse(
        pose: Pose,
        timestampMillis: Long,
    ): ExerciseFrameAnalysis {
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
            return invalidFrame(timestampMillis)
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
            return invalidFrame(timestampMillis)
        }

        val leftKneeAngle = CalculateAngleUtil.calculate(leftHip, leftKnee, leftAnkle)
        val rightKneeAngle = CalculateAngleUtil.calculate(rightHip, rightKnee, rightAnkle)
        val averageKneeAngle = CalculateAngleUtil.calculateAverageAngle(leftKneeAngle, rightKneeAngle)
        val legSymmetryDifference = abs(leftKneeAngle - rightKneeAngle)
        val repEvent = updatePhase(averageKneeAngle)

        val metrics = buildMap {
            put(SquatFormMetrics.averageKneeAngle, averageKneeAngle)
            put(SquatFormMetrics.leftKneeAngle, leftKneeAngle)
            put(SquatFormMetrics.rightKneeAngle, rightKneeAngle)
            put(SquatFormMetrics.legSymmetry, legSymmetryDifference)
            if (repEvent == RepEvent.COMPLETED) {
                put(SquatFormMetrics.lockoutAngle, averageKneeAngle)
            }
        }

        return ExerciseFrameAnalysis(
            timestamp = timestampMillis,
            isValidPose = true,
            repEvent = repEvent,
            metrics = metrics,
        )
    }

    private fun updatePhase(kneeAngle: Double): RepEvent {
        return when (phase) {
            // User is standing, wait for knee angle to decrease
            SquatPhase.STANDING -> {
                if (kneeAngle < config.descendingAngle) {
                    phase = SquatPhase.DESCENDING
                    RepEvent.STARTED
                } else {
                    RepEvent.NONE
                }
            }

            // User is descending, wait for knee angle to reach bottom
            SquatPhase.DESCENDING -> {
                when {
                    kneeAngle <= config.bottomAngle -> {
                        phase = SquatPhase.BOTTOM
                        RepEvent.NONE
                    }

                    // The user started moving down but returned to standing without reaching the bottom position.
                    kneeAngle >= config.standingAngle -> {
                        phase = SquatPhase.STANDING
                        RepEvent.CANCELLED
                    }

                    else -> {
                        RepEvent.NONE
                    }
                }
            }

            // User is at the bottom, wait for knee angle to increase
            SquatPhase.BOTTOM -> {
                if (kneeAngle > config.ascendingAngle) {
                    phase = SquatPhase.ASCENDING
                }
                RepEvent.NONE
            }

            // User is ascending, wait for knee angle to reach standing
            SquatPhase.ASCENDING -> {
                when {
                    kneeAngle >= config.standingAngle -> {
                        phase = SquatPhase.STANDING
                        reps++
                        RepEvent.COMPLETED
                    }
                    // The user moved down again before finishing. Continue tracking the same rep.
                    kneeAngle <= config.bottomAngle -> {
                        phase = SquatPhase.BOTTOM
                        RepEvent.NONE
                    }

                    else -> {
                        RepEvent.NONE
                    }
                }
            }
        }
    }

    private fun invalidFrame(timeStampMillis: Long): ExerciseFrameAnalysis {
        return ExerciseFrameAnalysis(
            timestamp = timeStampMillis,
            isValidPose = false,
            repEvent = RepEvent.NONE,
            metrics = emptyMap(),
        )
    }

    private fun calculateAverageFormScore(): Int? {
        if (completedRepScores.isEmpty()) return null
        return completedRepScores.average().toInt()
    }

    override fun reset() {
        startNewSet()
        lastRepFormScore = null
        completedRepScores.clear()
    }

    fun startNewSet() {
        phase = SquatPhase.STANDING
        reps = 0

        formScorer.reset()
    }
}
