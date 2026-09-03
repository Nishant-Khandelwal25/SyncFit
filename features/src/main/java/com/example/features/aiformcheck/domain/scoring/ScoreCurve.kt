package com.example.features.aiformcheck.domain.scoring

fun interface ScoreCurve {
    fun calculate(value: Double): Int
}
