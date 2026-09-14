package com.example.syncfit_core.api.model.result

data class ExerciseInfoResult(
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
)
