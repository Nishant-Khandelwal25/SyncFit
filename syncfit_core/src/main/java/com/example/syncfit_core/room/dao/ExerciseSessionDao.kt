package com.example.syncfit_core.room.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import com.example.syncfit_core.room.entity.ExerciseSession
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseSessionDao {
    @Query("""SELECT * FROM exercise_session ORDER BY startedAt DESC""")
    fun getExerciseInfo(): Flow<List<ExerciseSession>>

    @Upsert
    suspend fun upsertExerciseInfo(exerciseSession: ExerciseSession)
}
