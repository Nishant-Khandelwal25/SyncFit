package com.example.syncfit_core.api.mapper

import com.example.syncfit_core.api.model.result.ExerciseInfoApiResult
import com.example.syncfit_core.api.model.result.ExerciseInfoResult
import com.example.syncfit_core.api.model.response.ExerciseInfoApiResponse
import com.example.syncfit_core.api.model.response.ExerciseInfoResponse
import com.example.syncfit_core.room.entity.ExerciseInfoEntity

fun ExerciseInfoResult.toExerciseInfoEntity(
    requestedExerciseName: String,
): ExerciseInfoEntity {
    return ExerciseInfoEntity(
        cacheKey = requestedExerciseName.toExerciseCacheKey(),
        id = id,
        exerciseName = exerciseName,
        exerciseVariations = exerciseVariations,
        primaryMuscle = primaryMuscle,
        targetedMuscleGroup = targetedMuscleGroup,
        secondaryMuscle = secondaryMuscle,
        equipment = equipment,
        howToPerform = howToPerform,
        exerciseLevel = exerciseLevel,
        isAiFormCheckEnabled = isAiFormCheckEnabled,
        demonstrationLink = demonstrationLink,
        lastUpdatedMillis = System.currentTimeMillis(),
    )
}

fun ExerciseInfoEntity.toExerciseInfoResult(): ExerciseInfoResult {
    return ExerciseInfoResult(
        id = id,
        exerciseName = exerciseName,
        exerciseVariations = exerciseVariations,
        primaryMuscle = primaryMuscle,
        targetedMuscleGroup = targetedMuscleGroup,
        secondaryMuscle = secondaryMuscle,
        equipment = equipment,
        howToPerform = howToPerform,
        exerciseLevel = exerciseLevel,
        isAiFormCheckEnabled = isAiFormCheckEnabled,
        demonstrationLink = demonstrationLink,
    )
}

fun ExerciseInfoApiResponse?.toExerciseInfoApiResult(): ExerciseInfoApiResult {
    return ExerciseInfoApiResult(
        success = this?.success ?: false,
        message = this?.message ?: "Something went wrong.",
        exerciseInfo = this?.exerciseInfo.toExerciseInfoResult(),
    )
}

fun ExerciseInfoResponse?.toExerciseInfoResult(): ExerciseInfoResult {
    return ExerciseInfoResult(
        id = this?.id ?: 0,
        exerciseName = this?.exerciseName.orEmpty(),
        exerciseVariations = this?.exerciseVariations ?: emptyList(),
        primaryMuscle = this?.primaryMuscle.orEmpty(),
        targetedMuscleGroup = this?.targetedMuscleGroup ?: emptyList(),
        secondaryMuscle = this?.secondaryMuscle.orEmpty(),
        equipment = this?.equipment.orEmpty(),
        howToPerform = this?.howToPerform ?: emptyList(),
        exerciseLevel = this?.exerciseLevel.orEmpty(),
        demonstrationLink = this?.demonstrationLink.orEmpty(),
        isAiFormCheckEnabled = this?.isAiFormCheckEnabled ?: false,
    )
}

fun String.toExerciseCacheKey(): String {
    return trim().lowercase()
}
