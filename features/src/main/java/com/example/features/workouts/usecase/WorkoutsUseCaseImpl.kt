package com.example.features.workouts.usecase

import com.example.features.workouts.model.ExerciseInfo
import com.example.syncfit_core.room.model.ExerciseType
import javax.inject.Inject

class WorkoutsUseCaseImpl @Inject constructor() : WorkoutsUseCase {
    override suspend fun getAllWorkouts(): List<ExerciseInfo> {
        return buildList {
            add(
                ExerciseInfo(
                    exerciseName = "Barbell Squats",
                    bodyPart = "Legs",
                    exerciseType = ExerciseType.SQUAT,
                ),
            )
            add(
                ExerciseInfo(
                    exerciseName = "Bicep Curl",
                    bodyPart = "Arms",
                    exerciseType = ExerciseType.BICEP_CURL,
                ),
            )
            add(
                ExerciseInfo(
                    exerciseName = "Deadlift",
                    bodyPart = "Back",
                    exerciseType = ExerciseType.DEADLIFT,
                ),
            )
            add(
                ExerciseInfo(
                    exerciseName = "Dumbbell Lateral Raise",
                    bodyPart = "Shoulders",
                    exerciseType = ExerciseType.DUMBBELL_LATERAL_RAISE,
                ),
            )
            add(
                ExerciseInfo(
                    exerciseName = "Flat Chest Press",
                    bodyPart = "Chest",
                    exerciseType = ExerciseType.FLAT_CHEST_PRESS,
                ),
            )
            add(
                ExerciseInfo(
                    exerciseName = "Incline Chest Press",
                    bodyPart = "Chest",
                    exerciseType = ExerciseType.INCLINE_CHEST_PRESS,
                ),
            )
            add(
                ExerciseInfo(
                    exerciseName = "Leg Press",
                    bodyPart = "Legs",
                    exerciseType = ExerciseType.LEG_PRESS,
                ),
            )
            add(
                ExerciseInfo(
                    exerciseName = "Shoulder Press",
                    bodyPart = "Shoulders",
                    exerciseType = ExerciseType.SHOULDER_PRESS,
                ),
            )
            add(
                ExerciseInfo(
                    exerciseName = "Tricep Extension",
                    bodyPart = "Arms",
                    exerciseType = ExerciseType.TRICEP_EXTENSION,
                ),
            )
        }
    }
}
