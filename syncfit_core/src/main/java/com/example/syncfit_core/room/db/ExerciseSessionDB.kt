package com.example.syncfit_core.room.db

import androidx.room3.Database
import androidx.room3.RoomDatabase
import com.example.syncfit_core.room.dao.ExerciseSessionDao
import com.example.syncfit_core.room.entity.ExerciseSession

@Database(entities = [ExerciseSession::class], version = 2)
abstract class ExerciseSessionDB : RoomDatabase() {
    abstract fun exerciseSessionDao(): ExerciseSessionDao
}
