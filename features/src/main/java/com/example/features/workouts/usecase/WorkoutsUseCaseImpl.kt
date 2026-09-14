package com.example.features.workouts.usecase

import com.example.features.workouts.model.WorkoutInfo
import com.example.syncfit_core.room.model.ExerciseType
import javax.inject.Inject

class WorkoutsUseCaseImpl @Inject constructor() : WorkoutsUseCase {
    override suspend fun getAllWorkouts(): List<WorkoutInfo> {
        return buildList {
            add(
                WorkoutInfo(
                    exerciseName = "Squat",
                    bodyPart = "Legs",
                    exerciseType = ExerciseType.SQUAT,
                ),
            )
            add(
                WorkoutInfo(
                    exerciseName = "Bicep Curl",
                    bodyPart = "Arms",
                    exerciseType = ExerciseType.BICEP_CURL,
                ),
            )
            add(
                WorkoutInfo(
                    exerciseName = "Deadlift",
                    bodyPart = "Back",
                    exerciseType = ExerciseType.DEADLIFT,
                ),
            )
            add(
                WorkoutInfo(
                    exerciseName = "Lateral Raise",
                    bodyPart = "Shoulders",
                    exerciseType = ExerciseType.LATERAL_RAISE,
                ),
            )
            add(
                WorkoutInfo(
                    exerciseName = "Flat Chest Press",
                    bodyPart = "Chest",
                    exerciseType = ExerciseType.FLAT_CHEST_PRESS,
                ),
            )
            add(
                WorkoutInfo(
                    exerciseName = "Incline Chest Press",
                    bodyPart = "Chest",
                    exerciseType = ExerciseType.INCLINE_CHEST_PRESS,
                ),
            )
            add(
                WorkoutInfo(
                    exerciseName = "Leg Press",
                    bodyPart = "Legs",
                    exerciseType = ExerciseType.LEG_PRESS,
                ),
            )
            add(
                WorkoutInfo(
                    exerciseName = "Shoulder Press",
                    bodyPart = "Shoulders",
                    exerciseType = ExerciseType.SHOULDER_PRESS,
                ),
            )
            add(
                WorkoutInfo(
                    exerciseName = "Tricep Extension",
                    bodyPart = "Arms",
                    exerciseType = ExerciseType.TRICEP_EXTENSION,
                ),
            )
        }
    }
}
