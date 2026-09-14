package com.example.syncfit_core.room.entity

import androidx.room3.Entity
import androidx.room3.PrimaryKey

@Entity(tableName = "exercise_info")
data class ExerciseInfoEntity(
    @PrimaryKey
    val cacheKey: String,
    val id: Int,
    val exerciseName: String,
    val exerciseVariations: List<String>,
    val primaryMuscle: String,
    val targetedMuscleGroup: List<String>,
    val secondaryMuscle: String,
    val equipment: String,
    val howToPerform: List<String>,
    val exerciseLevel: String,
    val isAiFormCheckEnabled: Boolean,
    val demonstrationLink: String,
    val lastUpdatedMillis: Long,
)
