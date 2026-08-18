package com.example.features.aiformcheck.domain.repository

import com.example.features.aiformcheck.domain.model.Pose
import kotlinx.coroutines.flow.Flow

interface PoseRepository {
    val poses: Flow<Pose>
}
