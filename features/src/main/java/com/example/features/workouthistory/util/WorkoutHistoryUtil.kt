package com.example.features.workouthistory.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object WorkoutHistoryUtil {
    fun Long.toFormatDateAndTime(): String {
        val formatter = SimpleDateFormat("MMM dd, yyyy : HH:mm", Locale.getDefault())

        return formatter.format(Date(this))
    }
}
