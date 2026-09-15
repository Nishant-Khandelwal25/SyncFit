package com.example.syncfit_core.room.db

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.syncfit_core.room.converter.ExerciseInfoTypeConverters
import com.example.syncfit_core.room.dao.ExerciseInfoDao
import com.example.syncfit_core.room.dao.ExerciseSessionDao
import com.example.syncfit_core.room.dao.ExercisesListDao
import com.example.syncfit_core.room.entity.ExerciseInfoEntity
import com.example.syncfit_core.room.entity.ExerciseSession
import com.example.syncfit_core.room.entity.ExercisesListEntity

@Database(
    entities = [
        ExerciseSession::class,
        ExerciseInfoEntity::class,
        ExercisesListEntity::class,
    ],
    version = 4,
)
@ColumnTypeConverters(ExerciseInfoTypeConverters::class)
abstract class ExerciseSessionDB : RoomDatabase() {
    abstract fun exerciseSessionDao(): ExerciseSessionDao
    abstract fun exerciseInfoDao(): ExerciseInfoDao
    abstract fun getExercisesListDao(): ExercisesListDao
}
