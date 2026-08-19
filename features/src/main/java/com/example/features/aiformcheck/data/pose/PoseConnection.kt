package com.example.features.aiformcheck.data.pose

data class PoseConnection(
    val start: Int,
    val end: Int,
)

val POSE_CONNECTIONS = listOf(

    // Head
    PoseConnection(0, 1),
    PoseConnection(1, 2),
    PoseConnection(2, 3),
    PoseConnection(3, 7),

    PoseConnection(0, 4),
    PoseConnection(4, 5),
    PoseConnection(5, 6),
    PoseConnection(6, 8),

    // Torso
    PoseConnection(11, 12),
    PoseConnection(11, 23),
    PoseConnection(12, 24),
    PoseConnection(23, 24),

    // Left arm
    PoseConnection(11, 13),
    PoseConnection(13, 15),

    // Right arm
    PoseConnection(12, 14),
    PoseConnection(14, 16),

    // Left leg
    PoseConnection(23, 25),
    PoseConnection(25, 27),

    // Right leg
    PoseConnection(24, 26),
    PoseConnection(26, 28),
)
