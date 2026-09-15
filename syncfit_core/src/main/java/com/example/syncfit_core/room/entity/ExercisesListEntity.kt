package com.example.syncfit_core.room.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey
import com.example.syncfit_core.room.model.ExerciseType

@Entity("exercises_list")
data class ExercisesListEntity(
    @PrimaryKey
    val id: Int,
    val exerciseName: String,
    val bodyPart: String,
    val exerciseType: ExerciseType,
    val lastUpdatedMillis: Long,
)
