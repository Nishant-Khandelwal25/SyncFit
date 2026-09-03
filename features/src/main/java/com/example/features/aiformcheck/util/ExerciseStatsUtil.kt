package com.example.features.aiformcheck.util

import android.annotation.SuppressLint

object ExerciseStatsUtil {
    @SuppressLint("DefaultLocale")
    fun Long.formatTime(): String {
        val minutes = (this % 3600000) / 60000
        val seconds = (this % 60000) / 1000
        return String.format("%02d:%02d", minutes, seconds)
    }

    fun getFormScoreFeedback(score: Int?): Int {
        return when (score) {
            in 90..100 -> com.example.features.R.string.great
            in 75..89 -> com.example.features.R.string.good
            in 50..74 -> com.example.features.R.string.average
            in 0..49 -> com.example.features.R.string.poor
            else -> com.example.features.R.string.empty_string
        }
    }
}
