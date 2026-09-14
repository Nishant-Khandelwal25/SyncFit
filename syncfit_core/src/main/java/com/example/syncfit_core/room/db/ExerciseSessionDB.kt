package com.example.syncfit_core.room.db

import androidx.room3.ColumnTypeConverters
import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.syncfit_core.room.converter.ExerciseInfoTypeConverters
import com.example.syncfit_core.room.dao.ExerciseInfoDao
import com.example.syncfit_core.room.dao.ExerciseSessionDao
import com.example.syncfit_core.room.entity.ExerciseInfoEntity
import com.example.syncfit_core.room.entity.ExerciseSession

@Database(
    entities = [
        ExerciseSession::class,
        ExerciseInfoEntity::class,
    ],
    version = 3,
)
@ColumnTypeConverters(ExerciseInfoTypeConverters::class)
abstract class ExerciseSessionDB : RoomDatabase() {
    abstract fun exerciseSessionDao(): ExerciseSessionDao
    abstract fun exerciseInfoDao(): ExerciseInfoDao
}
