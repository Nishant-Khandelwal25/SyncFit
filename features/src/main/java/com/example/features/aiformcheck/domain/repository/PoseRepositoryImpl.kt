package com.example.features.aiformcheck.domain.repository

import com.example.features.aiformcheck.data.pose.PoseDetector
import com.example.features.aiformcheck.domain.model.Pose
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PoseRepositoryImpl @Inject constructor(poseDetector: PoseDetector) : PoseRepository {
    override val poses: Flow<Pose> = poseDetector.results
}
