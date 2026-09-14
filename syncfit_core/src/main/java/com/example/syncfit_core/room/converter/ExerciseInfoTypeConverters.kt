package com.example.syncfit_core.room.converter

import androidx.room3.ColumnTypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ExerciseInfoTypeConverters {
    private val gson = Gson()

    @ColumnTypeConverter
    fun fromStringList(value: List<String>): String {
        return gson.toJson(value)
    }

    @ColumnTypeConverter
    fun toStringList(value: String): List<String> {
        val type = object : TypeToken<List<String>>() {}.type
        return gson.fromJson<List<String>>(value, type) ?: emptyList()
    }
}
