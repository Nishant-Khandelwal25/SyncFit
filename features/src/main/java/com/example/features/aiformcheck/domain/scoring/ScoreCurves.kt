package com.example.features.aiformcheck.domain.scoring

object ScoreCurves {
    fun lowerIsBetter(goodValue: Double, badValue: Double): ScoreCurve {
        require(badValue > goodValue) {
            "badValue must be greater than goodValue"
        }

        return ScoreCurve { value ->
            when {
                value <= goodValue -> 100
                value >= badValue -> 0
                else -> {
                    val score = ((badValue - value) / (badValue - goodValue))
                    (score * 100).toInt().coerceIn(0, 100)
                }
            }
        }
    }

    fun higherIsBetter(goodValue: Double, badValue: Double): ScoreCurve {
        require(badValue < goodValue) {
            "goodValue must be greater than badValue"
        }

        return ScoreCurve { value ->
            when {
                value >= goodValue -> 100
                value <= badValue -> 0
                else -> {
                    val score = ((value - badValue) / (goodValue - badValue))
                    (score * 100).toInt().coerceIn(0, 100)
                }
            }
        }
    }

    fun idealRange(
        hardMinimum: Double,
        idealMinimum: Double,
        idealMaximum: Double,
        hardMaximum: Double,
    ): ScoreCurve {
        require(hardMinimum < idealMinimum) { "hardMinimum must be less than idealMinimum" }
        require(idealMinimum <= idealMaximum) { "idealMinimum must not exceed idealMaximum" }
        require(idealMaximum < hardMaximum) { "idealMaximum must be less than hardMaximum" }

        return ScoreCurve { value ->
            when {
                value < hardMinimum -> 0
                value < idealMinimum -> {
                    val score = ((value - hardMinimum) / (idealMinimum - hardMinimum))
                    (score * 100).toInt().coerceIn(0, 100)
                }

                value <= idealMaximum -> 100

                else -> {
                    val score = ((hardMaximum - value) / (hardMaximum - idealMaximum))
                    (score * 100).toInt().coerceIn(0, 100)
                }
            }
        }
    }
}
