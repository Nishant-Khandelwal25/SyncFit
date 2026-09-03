package com.example.syncfit_core.room.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.syncfit_core.room.model.ExerciseType

@Entity("exercise_session")
data class ExerciseSession(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val exerciseType: ExerciseType,
    val repsCount: Int?,
    val setsCount: Int?,
    val startedAt: Long?,
    val endedAt: Long?,
    val formScore: Int?,
)
