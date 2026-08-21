package com.example.features.aiformcheck.util

import android.annotation.SuppressLint

object ExerciseStatsUtil {
    @SuppressLint("DefaultLocale")
    fun Long.formatTime(): String {
        val minutes = (this % 3600000) / 60000
        val seconds = (this % 60000) / 1000
        return String.format("%02d:%02d", minutes, seconds)
    }
}
