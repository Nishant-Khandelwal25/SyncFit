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
}
