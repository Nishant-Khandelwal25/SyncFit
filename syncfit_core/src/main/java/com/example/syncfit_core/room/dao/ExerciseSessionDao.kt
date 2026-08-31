package com.example.syncfit_core.room.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import com.example.syncfit_core.room.entity.ExerciseSession

@Dao
interface ExerciseSessionDao {
    @Query("SELECT * FROM exercise_session")
    suspend fun getExerciseInfo(): List<ExerciseSession>

    @Upsert
    suspend fun upsertExerciseInfo(exerciseSession: ExerciseSession)
}
