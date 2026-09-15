package com.example.syncfit_core.room.dao

import androidx.room3.Dao
import androidx.room3.Query
import androidx.room3.Transaction
import androidx.room3.Upsert
import com.example.syncfit_core.room.entity.ExercisesListEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExercisesListDao {
    @Query("""SELECT * FROM exercises_list ORDER BY id ASC""")
    fun observeExercisesList(): Flow<List<ExercisesListEntity>>

    @Upsert
    suspend fun upsertExercisesList(exercisesList: List<ExercisesListEntity>)

    @Query("DELETE FROM exercises_list")
    suspend fun clearExercisesList()

    @Transaction
    suspend fun replaceExercisesList(
        exercisesList: List<ExercisesListEntity>,
    ) {
        clearExercisesList()
        upsertExercisesList(exercisesList)
    }
}
