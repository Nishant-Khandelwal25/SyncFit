package com.example.syncfit_core.room.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Upsert
import com.example.syncfit_core.room.entity.ExerciseInfoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseInfoDao {
    @Query("""SELECT * FROM exercise_info WHERE cacheKey = :cacheKey LIMIT 1""")
    fun observeExerciseInfo(cacheKey: String): Flow<ExerciseInfoEntity?>

    @Upsert
    suspend fun upsertExerciseInfo(exerciseInfo: ExerciseInfoEntity)
}
