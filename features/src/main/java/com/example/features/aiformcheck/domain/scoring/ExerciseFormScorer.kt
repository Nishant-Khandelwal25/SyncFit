package com.example.features.aiformcheck.domain.scoring

class ExerciseFormScorer(private val profile: ExerciseScoringProfile) {
    private val metricSamples = mutableMapOf<FormMetricKey, MutableList<Double>>()
    private var isTrackingRep = false
    private var repStartedAt = 0L
    private var totalFrames = 0
    private var validFrames = 0

    /**
     * Processes a frame of exercise data and updates the scoring state.
     *
     * @param frame The frame of exercise data to process.
     * @return An [ExerciseScoringUpdate] containing the live score and any completed score if applicable.
     */
    fun processFrame(frame: ExerciseFrameAnalysis): ExerciseScoringUpdate {
        if (frame.repEvent == RepEvent.CANCELLED) {
            reset()
            return ExerciseScoringUpdate()
        }

        if (frame.repEvent == RepEvent.STARTED) {
            startRep(frame.timestamp)
        }

        if (!isTrackingRep) {
            return ExerciseScoringUpdate()
        }

        totalFrames++

        if (frame.isValidPose) {
            validFrames++
            frame.metrics.forEach { (metricKey, value) ->
                metricSamples.getOrPut(metricKey) { mutableListOf() }.add(value)
            }
        }

        if (frame.repEvent != RepEvent.COMPLETED) {
            return ExerciseScoringUpdate()
        }

        val durationMillis = (frame.timestamp - repStartedAt).coerceAtLeast(0L)
        metricSamples.getOrPut(CommonFormMetrics.repDuration) {
            mutableListOf()
        }.add(durationMillis.toDouble())

        val completedScore = finishRep(durationMillis)
        return ExerciseScoringUpdate(completedScore = completedScore)
    }

    /**
     * Starts tracking a new repetition.
     *
     * @param timestampMillis The timestamp in milliseconds when the repetition started.
     */
    private fun startRep(timestampMillis: Long) {
        reset()
        isTrackingRep = true
        repStartedAt = timestampMillis
    }

    /**
     * Finalizes the scoring for a completed repetition and returns the overall score and feedback.
     *
     * @param durationMillis The duration of the repetition in milliseconds.
     * @return An [ExerciseFormScore] containing the overall score, metric scores, feedback, confidence, and duration.
     */
    private fun finishRep(durationMillis: Long): ExerciseFormScore {
        val confidence = if (totalFrames == 0) 0f else validFrames.toFloat() / totalFrames
        val metricScores = calculateMetricScores()
        val missingRequireMetric = profile.metricRules.filter { it.required }.any { rule ->
            metricScores.none { it.metricKey == rule.metricKey }
        }
        val isReliable = confidence >= profile.minimumValidFrameRatio

        val overallScore = if (isReliable && !missingRequireMetric) calculateWeightedScore(metricScores) else null

        val feedback = when {
            !isReliable -> listOf("Make sure your full body remains visible")

            missingRequireMetric -> listOf("Unable to calculate all form measurements")

            else -> createFeedback(metricScores)
        }

        val result = ExerciseFormScore(
            overallScore = overallScore,
            metricScores = metricScores,
            feedback = feedback,
            confidence = confidence,
            durationMillis = durationMillis,
        )

        reset()
        return result
    }

    /**
     * Calculates the metric scores based on the current metric samples and the scoring profile.
     *
     * @return A list of [MetricScore] objects representing the calculated scores for each metric.
     */
    private fun calculateMetricScores(): List<MetricScore> {
        return profile.metricRules.mapNotNull { rule ->
            val samples = metricSamples[rule.metricKey]
            if (samples.isNullOrEmpty()) return@mapNotNull null

            val aggregatedValue = aggregate(samples = samples, aggregation = rule.aggregation)

            MetricScore(
                metricKey = rule.metricKey,
                measuredValue = aggregatedValue,
                score = rule.scoreCurve.calculate(aggregatedValue),
            )
        }
    }

    /**
     * Calculates the weighted score based on the provided metric scores and the scoring profile.
     *
     * @param metricScore A list of [MetricScore] objects representing the calculated scores for each metric.
     * @return The weighted score as an integer, clamped between 0 and 100.
     */
    private fun calculateWeightedScore(metricScore: List<MetricScore>): Int {
        var weightedScore = 0.0
        var availableWeight = 0.0

        profile.metricRules.forEach { rule ->
            val metricScore = metricScore.firstOrNull {
                it.metricKey == rule.metricKey
            } ?: return@forEach

            weightedScore += metricScore.score * rule.weight
            availableWeight += rule.weight
        }

        if (availableWeight == 0.0) return 0

        return (weightedScore / availableWeight).toInt().coerceIn(0, 100)
    }

    /**
     * Creates feedback messages based on the provided metric scores and the scoring profile.
     *
     * @param metricScores A list of [MetricScore] objects representing the calculated scores for each metric.
     * @return A list of feedback messages as strings.
     */
    private fun createFeedback(metricScores: List<MetricScore>): List<String> {
        val worstRule = profile.metricRules.mapNotNull { rule ->
            val metricScore = metricScores.firstOrNull {
                it.metricKey == rule.metricKey
            } ?: return@mapNotNull null

            if (metricScore.score < rule.feedbackThreshold) {
                rule to metricScore
            } else {
                null
            }
        }.minByOrNull { (_, score) -> score.score }?.first
        return listOf(worstRule?.feedback ?: "Good Rep")
    }

    /**
     * Aggregates the provided samples based on the specified aggregation method.
     *
     * @param samples A list of sample values to aggregate.
     * @param aggregation The aggregation method to use (e.g., AVERAGE, MINIMUM, MAXIMUM).
     * @return The aggregated value as a Double.
     */
    private fun aggregate(samples: List<Double>, aggregation: MetricAggregation): Double {
        return when (aggregation) {
            MetricAggregation.AVERAGE -> samples.average()
            MetricAggregation.MINIMUM -> samples.minOrNull() ?: 0.0
            MetricAggregation.MAXIMUM -> samples.maxOrNull() ?: 0.0
            else -> samples.last()
        }
    }

    /**
     * Resets the scoring state, clearing metric samples and resetting tracking variables.
     */
    fun reset() {
        metricSamples.clear()
        isTrackingRep = false
        repStartedAt = 0L
        totalFrames = 0
        validFrames = 0
    }
}
