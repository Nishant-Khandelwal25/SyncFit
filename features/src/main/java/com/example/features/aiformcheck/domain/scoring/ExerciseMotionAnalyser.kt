package com.example.features.aiformcheck.domain.scoring

import com.example.features.aiformcheck.domain.model.Pose

interface ExerciseMotionAnalyser {
    fun analyse(pose: Pose, timestampMillis: Long): ExerciseFrameAnalysis

    fun reset()
}
