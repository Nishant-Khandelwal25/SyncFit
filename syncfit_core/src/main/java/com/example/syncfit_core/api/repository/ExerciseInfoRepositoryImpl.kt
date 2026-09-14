package com.example.syncfit_core.api.repository

import com.example.syncfit_core.api.model.ExerciseInfo
import com.example.syncfit_core.api.model.ExerciseInfoResponse
import com.example.syncfit_core.api.network.ApiService
import com.example.syncfit_core.api.network.NetworkResult
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.Response
import javax.inject.Inject

class ExerciseInfoRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
) : ExerciseInfoRepository {
    override fun getExerciseInfo(exerciseName: String): Flow<NetworkResult<ExerciseInfo>> =
        flow {
            emit(NetworkResult.Loading)
            try {
                val response = apiService.getExerciseInfo(exerciseName)
                val body = response.body()
                when {
                    response.isSuccessful && body?.success == true && body.exerciseInfo != null -> {
                        emit(NetworkResult.Success(data = body.exerciseInfo))
                    }

                    response.isSuccessful -> {
                        emit(
                            NetworkResult.Error(
                                message = body?.message ?: "Something went wrong.",
                                statusCode = response.code(),
                            ),
                        )
                    }

                    else -> {
                        emit(
                            NetworkResult.Error(
                                message = getApiErrorMessage(response) ?: response.message()
                                    .ifBlank { "Request Failed" },
                                statusCode = response.code(),
                            ),
                        )
                    }
                }
            } catch (ioException: IOException) {
                emit(
                    NetworkResult.Error(
                        message = "Check your internet connection and try again",
                        cause = ioException,
                    ),
                )
            } catch (exception: CancellationException) {
                throw exception
            } catch (exception: Exception) {
                emit(
                    NetworkResult.Error(
                        message = "Unable to process the server response",
                        cause = exception,
                    ),
                )
            }
        }


    private fun getApiErrorMessage(response: Response<*>): String? {
        return response.errorBody()?.use { errorBody ->
            runCatching {
                gson.fromJson(
                    errorBody.charStream(),
                    ExerciseInfoResponse::class.java,
                ).message
            }.getOrNull()
        }
    }
}
