package com.example.syncfit_core.api.repository

import com.example.syncfit_core.api.mapper.toExercisesListApiResult
import com.example.syncfit_core.api.mapper.toExercisesListEntity
import com.example.syncfit_core.api.mapper.toWorkoutInfoResult
import com.example.syncfit_core.api.model.response.ExercisesListApiResponse
import com.example.syncfit_core.api.model.result.WorkoutInfoResult
import com.example.syncfit_core.api.network.ApiService
import com.example.syncfit_core.api.network.RefreshResult
import com.example.syncfit_core.room.dao.ExercisesListDao
import com.google.gson.Gson
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import javax.inject.Inject

class ExercisesListRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val gson: Gson,
    private val dao: ExercisesListDao,
) : ExercisesListRepository {
    override fun observeExercisesList(): Flow<List<WorkoutInfoResult>> {
        return dao.observeExercisesList().map {
            it.map { exerciseItem ->
                exerciseItem.toWorkoutInfoResult()
            }
        }
    }

    override suspend fun refreshExercisesList(): RefreshResult {
        return try {
            val response = apiService.getExercises()
            val apiResult = response.body().toExercisesListApiResult()
            val exercisesList = apiResult.exercisesList

            when {
                response.isSuccessful && apiResult.success && exercisesList != null -> {
                    val exercisesListEntity = exercisesList.map { it.toExercisesListEntity() }
                    dao.replaceExercisesList(exercisesListEntity)
                    RefreshResult.Success
                }

                response.isSuccessful -> {
                    RefreshResult.Error(apiResult.message.ifBlank { "Exercises List information is unavailable." })
                }

                else -> {
                    RefreshResult.Error(
                        getApiErrorMessage(response) ?: response.message()
                            .ifBlank { "Unable to refresh exercises list information." },
                    )
                }
            }
        } catch (_: ConnectException) {
            RefreshResult.Error("Uh Oh!. Looks like our services are down. Please try again later.")
        } catch (_: SocketTimeoutException) {
            RefreshResult.Error("Connection timed out.")
        } catch (_: IOException) {
            RefreshResult.Error("You are offline. Please connect to the internet to fetch information.")
        } catch (exception: CancellationException) {
            throw exception
        } catch (_: Exception) {
            RefreshResult.Error("Unable to refresh exercise information.")
        }
    }

    private fun getApiErrorMessage(response: Response<*>): String? {
        return response.errorBody()?.use { errorBody ->
            runCatching {
                gson.fromJson(
                    errorBody.charStream(),
                    ExercisesListApiResponse::class.java,
                ).message
            }.getOrNull()
        }
    }
}
