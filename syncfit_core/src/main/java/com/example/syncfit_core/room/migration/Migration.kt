package com.example.syncfit_core.room.migration

import androidx.room3.migration.Migration
import androidx.sqlite.SQLiteConnection
import androidx.sqlite.execSQL

object Migration {
    val MIGRATION_1_2 = object : Migration(1, 2) {
        override suspend fun migrate(connection: SQLiteConnection) {
            connection.execSQL("""ALTER TABLE exercise_session ADD COLUMN formScore INTEGER""".trimIndent())
        }
    }

    val MIGRATION_2_3 = object : Migration(2, 3) {
        override suspend fun migrate(connection: SQLiteConnection) {
            connection.execSQL(
                """CREATE TABLE IF NOT EXISTS exercise_info (
                cacheKey TEXT NOT NULL PRIMARY KEY,
                id INTEGER NOT NULL,
                exerciseName TEXT NOT NULL,
                exerciseVariations TEXT NOT NULL,
                primaryMuscle TEXT NOT NULL,
                targetedMuscleGroup TEXT NOT NULL,
                secondaryMuscle TEXT NOT NULL,
                equipment TEXT NOT NULL,
                howToPerform TEXT NOT NULL,
                exerciseLevel TEXT NOT NULL,
                isAiFormCheckEnabled INTEGER NOT NULL,
                demonstrationLink TEXT NOT NULL,
                lastUpdatedMillis INTEGER NOT NULL
                )
            """.trimIndent(),
            )
        }
    }

    val MIGRATION_3_4 = object : Migration(3, 4) {
        override suspend fun migrate(connection: SQLiteConnection) {
            connection.execSQL(
                """CREATE TABLE IF NOT EXISTS exercises_list (
                    id INTEGER NOT NULL PRIMARY KEY,
                    exerciseName TEXT NOT NULL,
                    bodyPart TEXT NOT NULL,
                    exerciseType TEXT NOT NULL,
                    lastUpdatedMillis INTEGER NOT NULL
                    )
            """.trimMargin(),
            )
        }
    }
}
