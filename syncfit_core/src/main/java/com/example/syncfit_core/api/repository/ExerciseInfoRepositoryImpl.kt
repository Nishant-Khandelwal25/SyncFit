package com.example.syncfit_core.api.repository

import com.example.syncfit_core.api.mapper.toExerciseCacheKey
import com.example.syncfit_core.api.mapper.toExerciseInfoApiResult
import com.example.syncfit_core.api.mapper.toExerciseInfoEntity
import com.example.syncfit_core.api.mapper.toExerciseInfoResult
import com.example.syncfit_core.api.model.response.ExerciseInfoApiResponse
import com.example.syncfit_core.api.model.result.ExerciseInfoResult
import com.example.syncfit_core.api.network.ApiService
import com.example.syncfit_core.api.network.RefreshResult
import com.example.syncfit_core.room.dao.ExerciseInfoDao
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class ExerciseInfoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val dao: ExerciseInfoDao,
) : ExerciseInfoRepository {
    override fun observeExerciseInfo(exerciseName: String): Flow<ExerciseInfoResult?> {
        return dao.observeExerciseInfo(exerciseName.toExerciseCacheKey()).map { entity ->
            entity?.toExerciseInfoResult()
        }
    }

    override suspend fun refreshExerciseInfo(exerciseName: String): RefreshResult {
        return try {
            val response = apiService.getExerciseInfo(exerciseName)
            val apiResult = response.body().toExerciseInfoApiResult()
            val exerciseInfo = apiResult.exerciseInfo
            when {
                response.isSuccessful && apiResult.success &&
                    exerciseInfo != null -> {
                    dao.upsertExerciseInfo(exerciseInfo.toExerciseInfoEntity(exerciseName))
                    RefreshResult.Success
                }

                response.isSuccessful -> {
                    RefreshResult.Error(apiResult.message.ifBlank { "Exercise information is unavailable." })
                }

                else -> {
                    RefreshResult.Error(
                        getApiErrorMessage(response) ?: response.message()
                            .ifBlank { "Unable to refresh exercise information." },
                    )
                }
            }
        } catch (_: IOException) {
            RefreshResult.Error(
                "You are offline. Please connect to the internet to fetch information.",
            )
        } catch (exception: CancellationException) {
            throw exception
        } catch (_: Exception) {
            RefreshResult.Error(
                "Unable to refresh exercise information.",
            )
        }
    }

    private fun getApiErrorMessage(response: Response<*>): String? {
        return response.errorBody()?.use { errorBody ->
            runCatching {
                gson.fromJson(
                    errorBody.charStream(),
                    ExerciseInfoApiResponse::class.java,
                ).message
            }.getOrNull()
        }
    }
}
