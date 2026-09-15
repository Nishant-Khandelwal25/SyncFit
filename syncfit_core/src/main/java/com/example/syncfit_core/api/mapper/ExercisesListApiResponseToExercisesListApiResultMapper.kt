package com.example.syncfit_core.api.mapper

import com.example.syncfit_core.api.model.response.ExercisesListApiResponse
import com.example.syncfit_core.api.model.response.WorkoutInfoResponse
import com.example.syncfit_core.api.model.result.ExercisesListApiResult
import com.example.syncfit_core.api.model.result.WorkoutInfoResult
import com.example.syncfit_core.room.entity.ExercisesListEntity

fun ExercisesListApiResponse?.toExercisesListApiResult(): ExercisesListApiResult {
    return ExercisesListApiResult(
        success = this?.success ?: false,
        message = this?.message.orEmpty(),
        exercisesList = this?.exercisesList?.mapNotNull { it.toWorkoutInfoResultOrNull() },
    )
}

fun WorkoutInfoResponse.toWorkoutInfoResultOrNull(): WorkoutInfoResult? {
    val exerciseId = id ?: return null
    val name = exerciseName?.takeIf { it.isNotBlank() } ?: return null
    val type = exerciseType ?: return null

    return WorkoutInfoResult(
        id = exerciseId,
        exerciseName = name,
        bodyPart = bodyPart.orEmpty(),
        exerciseType = type,
    )
}

fun ExercisesListEntity.toWorkoutInfoResult(): WorkoutInfoResult {
    return WorkoutInfoResult(
        id = this.id,
        exerciseName = this.exerciseName,
        bodyPart = this.bodyPart,
        exerciseType = this.exerciseType,
    )
}

fun WorkoutInfoResult.toExercisesListEntity(): ExercisesListEntity {
    return ExercisesListEntity(
        id = this.id,
        exerciseName = this.exerciseName,
        bodyPart = this.bodyPart,
        exerciseType = this.exerciseType,
        lastUpdatedMillis = System.currentTimeMillis(),
    )
}
